package com.example.logisticappswift.objects

var post_list = mutableListOf<CreatedPost>()

class CreatedPost(
    var posted_by:String,
    var title:String,
    var profile_img:String,
    var deliver_from:String,
    var deliver_to:String,
    var price_offer:String,
    var description:String = "none",
    var load:String,
    var post_id:String = "1",
    var status:String = "open"
)
