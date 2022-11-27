/**
 * @author Natalie Lee
 * @andrewID chiaenl
 */
package com.example.webtryout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Animal{
    @JsonProperty("name")
    String name;
    @JsonProperty("latin_name")
    String latin_name;
    @JsonProperty("animal_type")
    String animal_type;
    @JsonProperty("active_time")
    String active_time;
    @JsonProperty("length_min")
    double length_min;
    @JsonProperty("length_max")
    double length_max;
    @JsonProperty("weight_min")
    double weight_min;
    @JsonProperty("weight_max")
    double weight_max;
    @JsonProperty("lifespan")
    double lifespan;
    @JsonProperty("habitat")
    String habitat;
    @JsonProperty("diet")
    String diet;
    @JsonProperty("geo_range")
    String geo_range;
    @JsonProperty("image_link")
    String image_link;
    @JsonProperty("id")
    int id;
    public String getPicLink(){return image_link;}
    @Override
    public String toString() {
        return String.format("{\"name\": \"%s\",\"animal_type\": \"%s\",\"diet\": \"%s\", \"lifespan\": %f," +
                        "\"geo_range\": \"%s\", \"length_max\": %f, \"length_min\": %f," +
                        "\"weight_max\": %f, \"weight_min\": %f, \"image_link\":\"%s\"}",
                this.name, this.animal_type, this.diet, this.lifespan,
                this.geo_range, this.length_max, this.length_min,
                this.weight_max, this.weight_min, this.image_link);
    }
}
