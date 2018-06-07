import scrapy
import json

class TripAdvisorScraper(scrapy.Spider):
    name = "tripadvisor-location-spider"
    start_urls = ["https://www.tripadvisor.de/Restaurants-g187323-zfg9909-Berlin.html",
                "https://www.tripadvisor.de/Hotels-g187323-Berlin-Hotels.html", \
                  "https://www.tripadvisor.de/Hotels-g187323-c2-Berlin-Hotels.html", \
                  "https://www.tripadvisor.de/Restaurants-g187323-Berlin.html", \
                  "https://www.tripadvisor.de/Restaurants-g187323-zfg9900-Berlin.html", \
                  "https://www.tripadvisor.de/Restaurants-g187323-zfg9901-Berlin.html", \
                  "https://www.tripadvisor.de/Restaurants-g187323-zfg11776-zfn7281258-Berlin.html"]
    
    def parse(self, response):
        #Follow links to location pages
        for href in response.xpath('//a[@class="property_title"]/@href'):
            yield response.follow(href, self.parse_location)
        
        #print("Pagination: ", response.css('a.next::attr(href)'))
        #Follow pagination links
        for href in response.css('a.next::attr(href)'):
            yield response.follow(href, self.parse)
                                  
    
    def parse_location(self, response):
        data = response.xpath('//script[@type="application/ld+json"]/text()').extract_first()
        json_data = json.loads(data)
        name = json_data.get("name")
        url = json_data.get("url")
        imageUrl = json_data.get("image")
        priceRange = json_data.get("priceRange")
        aggregateRating = json_data.get("aggregateRating")
        rating = None
        reviewCount = None
        if(aggregateRating):
            rating = aggregateRating.get("ratingValue")
            reviewCount = aggregateRating.get("reviewCount")
        address = json_data.get("address")
        streetAddress = None
        addressLocality = None
        postalCode = None
        if(address):
            streetAddress = address.get("streetAddress")
            addressLocality = address.get("addressLocality")
            postalCode = address.get("postalCode")
        phone = response.xpath('//div[contains(text(), "Telefonnummer:")]/span[1]/text()').extract_first()
        email = response.xpath('//a[contains(@href, "mailto:")]/@href').extract_first()
        if(email):
            email = email.replace('mailto:', '')
        monday = response.xpath('//span[@class="day" and contains(text(), "Montag")]/following-sibling::span[1]/div[@class="hoursRange"]/text()').extract_first()
        tuesday = response.xpath('//span[@class="day" and contains(text(), "Dienstag")]/following-sibling::span[1]/div[@class="hoursRange"]/text()').extract_first()
        wednesday = response.xpath('//span[@class="day" and contains(text(), "Mittwoch")]/following-sibling::span[1]/div[@class="hoursRange"]/text()').extract_first()
        thursday = response.xpath('//span[@class="day" and contains(text(), "Donnerstag")]/following-sibling::span[1]/div[@class="hoursRange"]/text()').extract_first()
        friday = response.xpath('//span[@class="day" and contains(text(), "Freitag")]/following-sibling::span[1]/div[@class="hoursRange"]/text()').extract_first()
        saturday = response.xpath('//span[@class="day" and contains(text(), "Samstag")]/following-sibling::span[1]/div[@class="hoursRange"]/text()').extract_first()
        sunday = response.xpath('//span[@class="day" and contains(text(), "Sonntag")]/following-sibling::span[1]/div[@class="hoursRange"]/text()').extract_first()
        category = response.xpath('//div[contains(text(),"Küche")]/following-sibling::div[@class="content"]/a/text()').extract()
        if(url):
            url = "https://www.tripadvisor.de" + url
        location = dict(name=name, url=url, imageUrl=imageUrl, priceRange=priceRange, \
                        rating=rating, reviewCount=reviewCount, streetAddress=streetAddress,\
                        addressLocality=addressLocality, postalCode=postalCode, phone=phone, \
                        email=email, monday=monday, tuesday=tuesday, wednesday=wednesday, \
                        thursday=thursday, friday=friday, saturday=saturday, sunday=sunday, \
                        category=category)
        yield location
