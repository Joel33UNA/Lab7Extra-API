package com.example.background

class User {
    var id = 0
    var name = ""
    var username = ""
    var email = ""
    var phone = ""
    var website = ""

    constructor(id:Int, name:String, username:String, email:String, phone:String, website:String){
        this.id = id
        this.name = name
        this.username = username
        this.email = email
        this.phone = phone
        this.website = website
    }

    override fun toString(): String {
        return "User(id=$id,\n" +
                " name='$name',\n" +
                " username='$username',\n" +
                " email='$email',\n" +
                " phone='$phone',\n" +
                " website='$website')\n"
    }
}