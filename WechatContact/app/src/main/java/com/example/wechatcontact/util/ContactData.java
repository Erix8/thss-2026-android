package com.example.wechatcontact.util;

import com.example.wechatcontact.model.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Create contact list including entries and headers
 */
public class ContactData {

    public static List<ListItem> createList(){

        List<ListItem> list = new ArrayList<>();

        // Top function entries
        list.add(new ListItem(ListItem.TYPE_ENTRY,"New Friends",null));
        list.add(new ListItem(ListItem.TYPE_ENTRY,"Chat Only",null));
        list.add(new ListItem(ListItem.TYPE_ENTRY,"Group Chats",null));
        list.add(new ListItem(ListItem.TYPE_ENTRY,"Tags",null));
        list.add(new ListItem(ListItem.TYPE_ENTRY,"Official Accounts",null));

        // Contacts with headers for A-Z (ensure each letter has at least 8 contacts)
        list.add(new ListItem(ListItem.TYPE_HEADER, "A", "A"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Aaron", "A"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Abby", "A"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Adele", "A"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Adrian", "A"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Agnes", "A"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Aiden", "A"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Alice", "A"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "B", "B"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Bailey", "B"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Barbara", "B"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Beatrice", "B"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Ben", "B"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Benson", "B"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Bianca", "B"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Blake", "B"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "C", "C"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Caleb", "C"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Cameron", "C"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Candice", "C"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Carl", "C"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Carmen", "C"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Carol", "C"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Charlie", "C"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "D", "D"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Daisy", "D"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Dale", "D"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Damon", "D"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Dana", "D"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Daniel", "D"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Daphne", "D"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Derek", "D"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "E", "E"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Earl", "E"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Eden", "E"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Edgar", "E"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Eileen", "E"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Elena", "E"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Eli", "E"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Emma", "E"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "F", "F"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Fabian", "F"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Faith", "F"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Felicity", "F"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Felix", "F"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Fiona", "F"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Flora", "F"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Frank", "F"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "G", "G"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Gabriel", "G"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Gail", "G"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Gavin", "G"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Geoff", "G"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "George", "G"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Gina", "G"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Grace", "G"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "H", "H"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Hailey", "H"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Hank", "H"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Harper", "H"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Harris", "H"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Hazel", "H"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Helen", "H"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Henry", "H"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "I", "I"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Ian", "I"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Iris", "I"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Isaac", "I"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Isla", "I"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Ivy", "I"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Izzy", "I"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Ivan", "I"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "J", "J"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Jack", "J"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Jackson", "J"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Jacob", "J"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Jade", "J"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Jake", "J"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Jasmine", "J"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Jill", "J"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "K", "K"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Kaden", "K"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Kai", "K"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Karen", "K"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Katrina", "K"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Keith", "K"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Kelly", "K"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Kevin", "K"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "L", "L"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Lance", "L"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Laura", "L"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Lauren", "L"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Leah", "L"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Leo", "L"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Liam", "L"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Lily", "L"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "M", "M"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Mabel", "M"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Mack", "M"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Madeline", "M"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Marcus", "M"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Margot", "M"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Mark", "M"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Maya", "M"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "N", "N"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Nadia", "N"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Nancy", "N"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Neil", "N"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Nelson", "N"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Nell", "N"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Noah", "N"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Nora", "N"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "O", "O"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Oakley", "O"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Olga", "O"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Oliver", "O"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Olivia", "O"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Omar", "O"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Opal", "O"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Oscar", "O"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "P", "P"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Paige", "P"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Pamela", "P"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Pat", "P"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Patricia", "P"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Paul", "P"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Paula", "P"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Peter", "P"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "Q", "Q"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Quentin", "Q"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Queenie", "Q"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Quincy", "Q"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Quinn", "Q"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Quora", "Q"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Quentin2", "Q"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Quin", "Q"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "R", "R"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Rachael", "R"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Rachel", "R"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Ralph", "R"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Ramona", "R"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Randy", "R"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Ray", "R"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Rebecca", "R"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "S", "S"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Sabrina", "S"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Sal", "S"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Sam", "S"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Samantha", "S"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Sandra", "S"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Sara", "S"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Scott", "S"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "T", "T"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Tabitha", "T"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Talia", "T"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Tammy", "T"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Tanner", "T"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Tara", "T"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Taylor", "T"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Thomas", "T"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "U", "U"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Ubaldo", "U"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Ulla", "U"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Ulysses", "U"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Uma", "U"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Umar", "U"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Uri", "U"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Ursula", "U"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "V", "V"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Val", "V"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Valerie", "V"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Van", "V"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Vance", "V"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Vanessa", "V"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Vera", "V"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Victor", "V"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "W", "W"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Wade", "W"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Walker", "W"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Wendy", "W"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Wes", "W"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Whitney", "W"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Will", "W"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "William", "W"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "X", "X"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Xander", "X"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Xavier", "X"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Xena", "X"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Xiomara", "X"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Xinyi", "X"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Xiu", "X"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Xuan", "X"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "Y", "Y"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Yana", "Y"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Yara", "Y"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Yasmin", "Y"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Yelena", "Y"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Yohan", "Y"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Yoko", "Y"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Yvonne", "Y"));

        list.add(new ListItem(ListItem.TYPE_HEADER, "Z", "Z"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Zack", "Z"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Zane", "Z"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Zara", "Z"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Zelda", "Z"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Zia", "Z"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Zoe", "Z"));
        list.add(new ListItem(ListItem.TYPE_CONTACT, "Zuri", "Z"));

        return list;
    }
}