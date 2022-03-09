package com.example.logisticappswift.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

var post_list = mutableListOf<CreatedPost>()

//@Parcelize
//class CreatedPost(
//    var posted_by:String,
//    var title:String,
//    var profile_img:String,
//    var deliver_from:String,
//    var deliver_to:String,
//    var price_offer:String,
//    var description:String = "none",
//    var load:String,
//    var post_id:String = "1",
//    var status:String = "open"
//):Parcelable
@Parcelize
class CreatedPost(
    var posted_by:String,
    var title:String,
    var profile_img:String,
    var deliver_from:String,
    var deliver_to:String,
    var price_offer:String,
    var description:String?,
    var load:String,
    var post_id:String,
    var status:String
):Parcelable{
    constructor():this("","","","","","","","","","")
}
