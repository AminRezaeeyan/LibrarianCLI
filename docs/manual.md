# LibrarianCLI - User Manual

## Introduction
Welcome to the Library Management System! This console-based application allows you to manage library resources efficiently. Below are the available commands and their descriptions.

## Commands

1. **Adding New Users:**

   - Syntax: 
     
      1. `add-staff#<id>|<first name>|<last name>|<national id>|<birth year>|<address>|<role>`
      2. `add-professor#<id>|<first name>|<last name>|<national id>|<birth year>|<address>|<department>`
      3. `add-graduate-student#<id>|<first name>|<last name>|<national id>|<birth year>|<address>|<advisor name>`
      4. `add-undergraduate-student#<id>|<first name>|<last name>|<national id>|<birth year>|<address>`
   
    - Description: Adds a new user to the system
2. **Updating Users:**
   - Syntax: 
     
      1. `update-staff#<id>|<first name>|<last name>|<national id>|<birth year>|<address>|<role>`
      2. `update-professor#<id>|<first name>|<last name>|<national id>|<birth year>|<address>|<department>`
      3. `update-graduate-student#<id>|<first name>|<last name>|<national id>|<birth year>|<address>|<advisor name>`
      4. `update-undergraduate-student#<id>|<first name>|<last name>|<national id>|<birth year>|<address>`
   
    - Description: Updates an existing user. For the fields you don't want to update insert `-` character
   
3. **Removing Users:**
   - Syntax: 
      `remove-user#<id>`
   
    - Description: Removes an existing user from the system. Note that users who has borrowed some books and hasn't returned yet, can't be removed.
   
4. **Adding New Libraries:**
    - Syntax: `add-library#<id>|<name>|<address>|<establish year>|<table count>`
    - Description: Adds a new library to the system

5. **Updating Libraries:**
    - Syntax: `update-library#<id>|<name>|<address>|<establish year>|<table count>`
    - Description:  Updates an existing library. For the fields you don't want to update insert `-` character

6. **Removing Libraries:**
    - Syntax: `remove-library#<id>`
    - Description: Removes and existing library. Take care when using this command as it removes all of the resources inside the library too!

7. **Adding New Categories:**
    - Syntax: `add-category#<id>|<name>|<parent category id>`
    - Description: Adds a new category to the system

8. **Updating Categories:**
    - Syntax: `update-category#<id>|<name>|<parent category id>`
    - Description:  Updates an existing category. For the fields you don't want to update insert `-` character

9. **Removing Categories:**
   - Syntax: `remove-category#<id>`
   - Description:  Removes an existing category. Don't worry! Resources with the specified category won't be removed

10. **Adding New Resources:**
    - Syntax: 
      1. `add-borrowable-book#<id>|<library id>|<category id>|<title>|<author>|<publisher>|<publication year>|<copies count>`
      2. `add-purchasable-book#<id>|<library id>|<category id>|<title>|<author>|<publisher>|<publication year>|<copies count>|<price>|<discount percentage>`
      3. `add-treasure-book#<id>|<library id>|<category id>|<title>|<author>|<publisher>|<publication year>|<donor>`
      4. `add-thesis#<id>|<library id>|<category id>|<title>|<author>|<advisor name>|<defense year>|<copies count>`
    - Description: Adds a new resource to a library in system. If your resource doesn't have any category, use `null`. Note that resource id should be unique in each library
    - 
11. **Updating Resources:**
    - Syntax:
      1. `update-borrowable-book#<id>|<library id>|<category id>|<title>|<author>|<publisher>|<publication year>|<copies count>`
      2. `update-purchasable-book#<id>|<library id>|<category id>|<title>|<author>|<publisher>|<publication year>|<copies count>|<price>|<discount percentage>`
      3. `update-treasure-book#<id>|<library id>|<category id>|<title>|<author>|<publisher>|<publication year>|<donor>`
      4. `update-thesis#<id>|<library id>|<category id>|<title>|<author>|<advisor name>|<defense year>|<copies count>`
    - Description:  Updates an existing resource. For the fields you don't want to update insert `-` character

12. **Removing Resources:**
    - Syntax: `remove-resource#<id>|<library id>`
    - Description:  Removes an existing resource. Note that you can't remove the resources that has been borrowed

13. **Buy:**
    - Syntax: `buy#<user id>|<resource id>|<library id>`
    - Description:  Sells a resource to the user. Note that you can only sell purchasable books
    - 
14. **Read:**
    - Syntax: `read#<user id>|<resource id>|<library id>|<start time>|<end time>`
    - Description:  Lends a resource to the user for reading in library. Note that resource should be readable and only professors can read resources. Times should be in this format: `yyyy-MM-dd HH:mm`

15. **Borrow:**
    - Syntax: `borrow#<user id>|<resource id>|<library id>|<borrow time>`
    - Description:  Lends a resource to the user. Note that resource should be borrowable. Times should be in this format: `yyyy-MM-dd HH:mm`

16. **Return:**
    - Syntax: `return#<user id>|<resource id>|<library id>|<return time>`
    - Description:  Returns a resource from user to library. It also punishes the users who have returned after deadline. Note that resource should be borrowable. Times should be in this format: `yyyy-MM-dd HH:mm`

17. **Reporting Penalties:**
    - Syntax: `report-penalties-sum`
    - Description:  Reports the sum of penalties of users in the system

18. **Reporting Best Seller:**
    - Syntax: `report-best-seller#<library id>`
    - Description:  Reports the bestseller resource in a library

19. **Reporting Popular Borrowed Resources:**
    - Syntax: `report-most-borrowed#<library id>`
    - Description:  Reports the most borrowed resource in a library
20. **Searching Users:**
    - Syntax: `search-users`
    - Description:  Searches for the users in the system

21. **Searching Resources:**
    - Syntax: `search-resources`
    - Description:  Searches for the resources in the system

22. **Exit:**
    - Syntax: `exit`
    - Description: Exits the application

## Example Usage

- >add-library#13ab2|Central Library|Tehran|2021|45
- >add-category#abd|Computer Science|null
- >add-staff#1l34|Amin|Rezaeeyan|123|2005|address|educational staff
- >update-staff#1l34|-|-|-|-|new address|-
- >add-treasure-book#abd2r|13ab2|abd|treasure book|Ali Rezai|publisher|2001|Hossein Rezai
- >report-penalties-sum
- >exit
