import scrapy

class LocationSpider(scrapy.Spider):
    name = "prinz-location-spider"
    start_urls = ["http://prinz.de/berlin/locations/kategorie/sport-freizeit", \
                  "http://prinz.de/berlin/locations/kategorie/gastro", \
                  "http://prinz.de/berlin/locations/kategorie/kultur", \
                  "http://prinz.de/berlin/locations/kategorie/ausgehen", \
                  "http://prinz.de/berlin/locations/kategorie/stadtleben", \
                  "http://prinz.de/berlin/locations/kategorie/shopping-style"]
    
    def parse(self, response):
        #Follow links to location pages
        for href in response.css('div.col-xs-12 a::attr(href)'):
            yield response.follow(href, self.parse_location)
        
        print("Pagination: ", response.css('a.next::attr(href)'))
        #Follow pagination links
        for href in response.css('a.next::attr(href)'):
            yield response.follow(href, self.parse)
                                  
    
    def parse_location(self, response):
        name= response.xpath('//span[@itemprop="name"]/span/text()').extract_first()
        streetAddress = response.xpath('//span[@itemprop="streetAddress"]/text()').extract_first()
        postalCode = response.xpath('//span[@itemprop="postalCode"]/text()').extract_first()
        latitude = response.xpath('//meta[@property="place:location:latitude"]/@content').extract_first()
        longitude = response.xpath('//meta[@property="place:location:longitude"]/@content').extract_first()
        phone = response.xpath('//a[@itemprop="telephone"]/text()').extract_first()
        email = response.xpath('//a[@itemprop="email"]/text()').extract_first()
        website = response.xpath('//a[@itemprop="url"]/text()').extract_first()
        prinzRating = response.xpath('//div[@class="rating l"]/@data-rating').extract_first()
        publicTransportation =response.xpath('//span[@class="italic icon- prinz bus"]/following::dd/text()').extract_first()
        if publicTransportation:
            publicTransportation = publicTransportation.replace('\r', '').replace('\n', '')
        openingHours = []
        for element in response.xpath('//dl[@class="dl-attributes"]/dd/text()'):
            content = element.extract()
            if("\n" not in content and content != publicTransportation):
                openingHours.append(content)
        imageUrl = response.xpath('//img[@class="main_image"]/@src').extract_first()
        prinzUrl = response.xpath('//meta[@property="og:url"]/@content').extract_first()
        location = dict(name=name, streetAddress=streetAddress, postalCode=postalCode,  \
                   latitude=latitude, longitude=longitude, phone=phone, email=email, \
                   website=website, prinzRating=prinzRating, openingHours=";".join(openingHours), publicTransportation=publicTransportation, \
                   imageUrl=imageUrl, prinzUrl=prinzUrl)
        yield location        
