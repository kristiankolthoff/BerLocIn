# Introduction


In the vast amount of data sources offered in the Web, one can find plenty valuable information on locations and places of interest. 
These information sources provide useful knowledge like opening hours and price range to potential customers of local businesses like
restaurants, clubs and many more. Since the availability and accessibility of this information is crucial for the local businesses 
to increase customer awareness and attention, numerous web services and websites store and provide this information to customers.
However, customers potentially miss important information while considering only an isolated location or point of interest information 
provider. On the other hand, combining the location information providers in a manual fashion is a cumbersome task and requires large 
amounts of effort by crosschecking several websites. By unifying various information providers for supplying integrated location data
with an unified schema, we mitigate manual integration efforts and facilitate the access to the data in a single point.

The previous brief description of the problem of integrating multiple information sources on location and points of interest 
information will be further investigated in this Web Data Integration project for locations in Berlin. Therefore, we first of
all recognize the most valuable information providers for supplying the location information. Here, we will focus on not only 
well-known services provided by TripAdvisor, Yelp and Google but also on smaller and less known websites focusing entirely 
on places located in Germany like Prinz. Since not all of these services provide an API for easy access of the location information, 
we extract this information by crawling the corresponding websites using [Scrapy](https://scrapy.org/) or by directly
querying the available APIs using sophisticated strategies for resolving query restrictions. In addition to location information, we 
incorporate available user review information for the corresponding locations. To accomplish this supplementary class generation, 
we will exploit the same data sources and extend the implemented web scrapers to obtain additional review information. 
In the subsequent Section we briefly investigate the tasks for data translation. The Section afterwards introduces the strategies applied 
for integrating the various datasets resolve co-referent locations. Finally in the last Section we present insights and strategies for fusing the identified co-referent entities in the previous 
phase.

# 1. Data Translation

In this section, we briefly introduce and explain the datasets we selected for integrating location and 
review information for Berlin. Furthermore we illustrate the process for fetching and generating the datasets
in the first place. In addition, we present our preprocessing and mapping strategies we applied in order to 
transform the source schemata into the global target schema. A general overview of the individual datasets and 
their profiling information is depicted in the shown Figure.

![alt tag](https://raw.githubusercontent.com/kristiankolthoff/BerLocIn/master/src/main/resources/images/datasets.PNG)

Due to the missing REST APIs for TripAdvisor as well as Prinz data sources, we implemented two spiders for
harvesting the location and review information using the Python Web Scraping library Scrapy. Therefore, we 
implemented patterns for identifying the references containing the valuable location information as well as for
following links and exploring the entire website. The actual attributes for locations and reviews are extracted 
using expressions based on XPath. On the other hand, the Yelp REST API provides incomplete information (for example
E-Mail and Website address) and thus we are also extracting the remaining attributes by crawling the associated website.

### a. Target Schema

![alt tag](https://github.com/kristiankolthoff/BerLocIn/blob/master/src/main/resources/images/target.PNG)

The target schema is created in XML format using the text editor. The consolidated target schema consists of two classes, 
the Location and the Review, respectively. The overlapping important attributes of the previously described datasets were 
selected for the target schema as shown in the previous Figure. The both classes of location and review are in 
a 1:n relationship as a single location can contain multiple reviews. Note that the *openingHours* attribute contains 
starting *from* and ending *to* hours for each weekday individually. This *openingHours* attribute 
therefore can be seen as an additional complex class, however, for maintaining simplicity we visualize the *openingHours*
as a single attribute in the consolidated target schema in the Figure (created by Hasan Alabed).

### b. Transformation

Most of the data source attributes are mapped directly to the corresponding attributes in the consolidated target schema. 
However, some of the attributes require value transformation. For example, the price level of a location is represented 
using a single or multiple currency symbols (for example €, €€). To facilitate the fusion and creating a 
consolidated value range, we transform the price level into a fixed range of numbers. Note that also price ranges are 
contained (for example € - €€) that are transformed to their average value. In addition, the total location 
rating from TripAdvisor is extracted from a markup class attribute string (for example *bubble_rating_50*). 
Review creation dates are also transformed to a unique representation. Moreover, we applied regular expression tokenization
for splitting and extracting the information for multiple reviews from a string/JSON-array representation. However, the most 
difficult and demanding transformation are opening hours of Prinz, since this information misses essential semantic markup and 
is unstructured text. Hence, we developed a generic opening hours parser based on regular expressions in Python and applied the
preprocessing before the actual mapping.

# 2. Identity Resolution

In this section we briefly introduce the applied strategies for identity resolution including the generated goldstandard and its properties, reasonable matching rules with appropriate attribute comparator functions derived from the subsequent descriptions and the blocking key strategies for performance enhancements.

### a. Matching Rules

In order to determine the best matching rule, we implemented many comparators for different attributes (identifying contact attributes) involving different similarity measures and additional preprocessing for enhanced attribute similarity. In the following we present four selected matching rules.

![alt tag](https://raw.githubusercontent.com/kristiankolthoff/BerLocIn/master/src/main/resources/images/matchingrules.PNG)

Note that we experimented with many different settings for the weights, however, present only equally-weighted comparators among the matching rules to maintain comparability of the used similarity measures and selected attributes. The first matching rule **MR1** uses the normalized *name* attribute and computes Damerau-Levenshtein similarity.

![alt tag](https://raw.githubusercontent.com/kristiankolthoff/BerLocIn/master/src/main/resources/images/results.PNG)

The *postalCode* attribute is used as a second equally weighted similarity component by computing the string equality. As shown in the above Table the results are already satisfying, considering the simplicity of the matching rule. However, due to the generation of FP consisting of chains of locations, the precision is rather low. The second matching rule **MR2** includes three additional similarity components weighted equally. First, we introduce [Sift4](https://github.com/tdebatty/java-string-similarity\#sift4) similarity of the normalized *phone* attribute by applying [libphonenumber](https://github.com/googlei18n/libphonenumber). Secondly, we incorporate the Damerau-Levenshtein similarity of the *website*, using only the extracted domain. Finally, we add the *email* similarity by merging the Damerau-Levenshtein similarity of the local-parts and the domains using the F-beta-score. By introducing these additional identifying components, we provide valuable additional information and thus the precision increases as shown in the Table. Previously contained FP are considerably reduced by the stricter matching rule. As expected, this also results in an increased average of the individual F1-scores across the goldstandards denoted by F1-mac. In the third matching rule **MR3**, the *website* similarity is substituted by the *streetAddress* similarity, since it adds supplementary spatial information. In order to preprocess the *streetAddress* attribute, we remove specific tokens and apply regular expressions to extract the street name and number separately. To generate the final similarity, we merge the two resulting similarities as described previously. We also experimented with [libpostal](https://github.com/openvenues/libpostal), however, were not able to integrate the C project. Also Google API is used to augment address information with proper *latitude* and *longitude*. Similarly to the previous rule, the precision increases due to the additional restriction, however, recall decreases slightly based on the exclusion of non-confident true correspondences. Since the precision is increasing more, the overall F1-score improves again. Note that we focus on extracting high-quality correspondences by taking into account of missing some. In the remaining matching rule **MR4** the *streetAddress* similarity is augmented by the physical similarity based on *latitude* and *longitude* attributes using [geodesy](https://github.com/mgavaghan/geodesy). To accomplish this, we compute the maximum of the *streetAddress* and the coordinates similarity. Since co-referent locations often have different *streetAddress* due to resolution issues, adding the coordinates similarity is reasonable. This assumption is reflected in the results for P-Y shown in the Table. Not only recall but also precision increases. However, in the other two goldstandards these effects are not fully reflected, but still resulting in second best F1-mac-star. The conducted evaluation reveals that **MR3** performs best for P-TA and Y-TA, whereas **MR4** performs best for P-Y. If only one matching rule should be selected, **MR3** performs best based on the F1-mac-star.
 
### b. Blocking Key Strategies

![alt tag](https://raw.githubusercontent.com/kristiankolthoff/BerLocIn/master/src/main/resources/images/blockingkeys.PNG)

Considering the large size of the incorporated datasets and the resulting vast number of possible comparisons, we applied various blocking strategies using different blocking key generator functions. Based on the spatial property of the location entities, we devise three different grouping or key generator functions exploiting the physical address of the location. First, we simply use the *postalCode* attribute to partition Berlin into its smaller but cohesive subregions depicted in Figure a). This strategy is reasonable assuming that same real world locations share the same physical address. However, co-referent locations that moved into another *postalCode* area as described previously are not compared and thus cannot be resolved. In addition, co-referent locations with partially missing *postalCode* cannot be captured as well. In order to reduce the quantity of potentially missing co-reference resolution, we extend the captured areas by grouping the *postalCode* into their corresponding larger administrative districts visualized in Figure b). In order to obtain more flexible spatial partitioning, we introduce variable region partitioning which divides Berlin into *n x m* subregions as depicted in Figure c) for the case of *6 x 3* partitioning. Variable region partitioning can be tuned to achieve the best trade-off between high accuracy and increased comparison performance. Finally, as a non-spatial blocking key function, we simply exploit the category tags of the location entities and compare only identical location types to avoid unnecessary comparisons between, for example, restaurants and physicians. This blocking key functions appears to be the most reasonable one compared to the others, however, arises the challenge of creating a category alignment between different data sources. Accordingly, we created a category type alignment between hierarchical Prinz and Yelp category collections with a small excerpt illustrated in Figure d). Note that the application of category partition to TripAdvisor is not reasonable, since it only contains restaurants and hotels and therefore the resulting reduction ratio would be too small. 


![alt tag](https://raw.githubusercontent.com/kristiankolthoff/BerLocIn/master/src/main/resources/images/blockingkeysevaluation.PNG)

The Table shows the results of applying the different blocking keys to a P-Y goldstandard and using matching rule **MR3**. The best results are achieved by using simply the *postalCode* attribute resulting in a reduction ratio of 0.98. By increasing the spatial area using the respective districts, we produce more correspondences and also increase in performance.

# 3. Data Fusion

In this section we briefly introduce the applied strategies for data fusion including the fusion goldstandard, reasonable conflict resolution strategies with appropriate attribute fuser functions, added provenance data and the final density and accuracy of the fused dataset. Note that the fusion quality also depends highly on the generated correspondence sets. Hence we focus mostly on high-confident correspondences generated in the previous identity resolution phase. In addition, we added time and weight provenance information to the data fusion process. Based on the findings described previously, we mark TripAdvisor as the most outdated dataset, whereas the other two receive same creation time information. Since Prinz involved scraping and additionally contained more unstructured text in the extraction results, we assign the lowest weight to it. However, Yelp receives the highest weight assuming it as the dataset with the highest quality. The data from Google has been omitted in the fusion process, due to the fact, that some of the information from this source are used as ground truth for the evaluation of the different conflict resolution strategies. By excluding the data, the results shown in above table are not biased towards the information provided by the goldstandard.

### a. Conflict Resolution Strategies

For most of the attributes we created multiple conflict resolution strategies, however, we provide only an excerpt of the results of the different strategies as shown in the following Table. For example, for the *name* attribute the ShortestString resolution works best since some platforms tend to add redundant type information to the *name* like "Restaurant" or "Cafe" that are not represented in the actual name. For the *streetAddress* we experimented with MostRecent and Voting conflict resolution strategy. The results of using MostRecent are considerably better than only rely on Voting. For the *email* attribute Voting performs similarly well. The individual entities of the *reviews* attribute are fused by simply applying Union, since there is no integration on the reviews available. For the remaining interesting attributes, we developed our own conflict resolution implementation as they required a customized fusion strategy. For example, the individual *reviewCount* attribute values are summarized by a custom summing conflict resolution function. In oder to avoid biasing the fused *rating* value by naively applying Average resolution to the distinct values, we implemented a NormalizedRating conflict resolution function. This strategy weights the *rating* values based on the *reviewCounts* and hence computes a weighted average. We evaluated the fused *rating* value against the rating available on Google and achieve reasonable performance as depicted in the Table. Similar to the challenges faced in the extraction and parsing of the *openingHours* attribute values in the data translation phase, we encountered additional difficulties in fusing the *openingHours* values. In many cases the *openingHours* values are inconsistent. Hence, we developed two main conflict resolution strategies. First, we devised the OpeningHoursIntersection conflict resolution function computing the intersection of the individual *from* and *to* attributes for each day of the week separately. Therefore the strategy can be interpreted as fine-grained (hour- and daylevel) Voting. As a second strategy to solve *openingHours* value inconsistencies, we developed OpeningHoursUnion computing the union of the individual *from* and *to* values. The OpeningHoursUnion conflict resolution function can thus be interpreted as a simply Union resolution on the same fine-grained level as the previous strategy.

![alt tag](https://raw.githubusercontent.com/kristiankolthoff/BerLocIn/master/src/main/resources/images/datafusion.PNG)

Due to the described properties in the previous section about the \texttt{name} attribute, it is obvious that preferring ShorterString than Voting for the *name* attribute results in increased accuracy as reflected in the previous Table. Preferring the MostRecent value  of the *streetAddress* results also in an increased accuracy compared to applying simply Voting. Applying the NormalizedRatingAverage conflict resolution strategy to the *rating* attribute values and compare it against Google rating ground-truth allowing small differences appears to be a reasonable strategy as well based on the evaluation results on our goldstandard. For not only the *reviews* but also the *reviewCount* attribute values, we consider the union or the sum as correct, respectively. Finally, we compare the *openingHours* attribute conflict resolution strategies of intersection and union. Based on our goldstandard, we recognize that computing the union of the daily *openingHours* performs slightly better than being more strict by only allowing information the datasets agree on. The density of the attributes in the initial datasets are displayed in the datasets Figure, indicating many missing values for *email* and *website* attributes. We can also recognize that in the Prinz dataset valuable attributes like *phone* is missing for more than half of the extracted locations. The figure on the target schema shows the final density values of the fused dataset in the consolidated target schema. The overall density increased considerably compared to the initial density values of the individual datasets.
