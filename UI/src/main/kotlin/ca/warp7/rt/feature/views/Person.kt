package ca.warp7.rt.feature.views

import javafx.beans.property.SimpleStringProperty

class Person internal constructor(fName: String, lName: String, email: String, likes: String) {

    internal val firstName: SimpleStringProperty = SimpleStringProperty(fName)
    internal val lastName: SimpleStringProperty = SimpleStringProperty(lName)
    internal val email: SimpleStringProperty = SimpleStringProperty(email)
    internal val likes: SimpleStringProperty = SimpleStringProperty(likes)

    fun getFirstName(): String {
        return firstName.get()
    }

    fun setFirstName(fName: String) {
        firstName.set(fName)
    }

    fun getLastName(): String {
        return lastName.get()
    }

    fun setLastName(fName: String) {
        lastName.set(fName)
    }

    fun getEmail(): String {
        return email.get()
    }

    fun setEmail(fName: String) {
        email.set(fName)
    }

    fun getLikes(): String {
        return likes.get()
    }

    fun setLikes(likes: String) {
        this.likes.set(likes)
    }
}
