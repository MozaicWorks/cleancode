#!/usr/bin/env groovy

import groovy.transform.ToString


def createUsersFromUserNamesUsingForLoop(usernames){
    def users = []
    for(def username in usernames)
        users.add(new User(username: username))
    return users
}

def createUsersFromUserNamesUsingCollect(usernames){
    return usernames.collect{new User(username: it)}
}


@ToString
class User{
    def username
}


def usernames = ["John Doe", "Jane Doe", "Jimmy B", "Elvis Presley"]

println "Create users from username using for loop"
def usersUsingLoop = createUsersFromUserNamesUsingForLoop(usernames)
assert usersUsingLoop.username == usernames
println usersUsingLoop


println "Create users from username using collect (equivalent of map)"
def users = createUsersFromUserNamesUsingCollect(usernames)
assert users.username == usernames
println users
