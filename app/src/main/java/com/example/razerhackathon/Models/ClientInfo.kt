package com.example.razerhackathon.Models

//data class Question(var title:String = "",
//                    var category:String = "",
//                    var description:String = "",
//                    var hasImg: Boolean = false,
//                    var image : String = "",
//                    var userId : String = "",
//                    var username : String = "",
//                    var time_created : String = DateTimeFormatter
//                        .ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
//                        .withZone(ZoneId.of("Singapore"))
//                        .format(Instant.now()),
//                    var noOfComments : Int = 0){
//    var firebaseID :String = ""
//}

data class ClientInfo(
    var firstName: String = "",
    var lastName: String = "",
    var NRIC: String = "",
    var NRIC_Issued: String = "",
    var emailAddress: String = "",
    var password: String = "",
    var confirmPassword: String = ""
)