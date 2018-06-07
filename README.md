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
as a single attribute in the consolidated target schema in the Figure.

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

# 3. Data Fusion
