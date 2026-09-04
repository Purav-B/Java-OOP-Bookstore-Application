Bookstore Application

A Java-Swing desktop application for managing a bookstore, supporting separate owner and customer roles with persistent data storage and a loyalty-based customer status system.



Overview:

This project implements a single-window GUI bookstore system built with Java Swing in NetBeans. Owners can manage the book catalog and customer accounts, while customers can browse books, make purchases, and earn or redeem loyalty points that determine their membership status. The system was planned using UML use-case and class diagrams before implementation, with a strong emphasis on clean object-oriented design.



Features:

Owner Role: Login authentication, add/delete/view books, add/delete/view customers

Customer Role: Login authentication, browse and purchase books, redeem points toward purchases, view real-time points and status

Persistent Storage: Book and customer data saved and loaded from books.txt and customers.txt

Loyalty System: Customers are automatically classified as Silver or Gold based on accumulated points, with status displayed to both customers and the owner




Architecture:

GUI Layer: Built with Java Swing panels for login, owner management, and customer interactions, structured within a main BookStoreFrame

Backend Layer: A BookStore class handles login validation, book/customer management, and purchase processing, including file I/O for persistent storage

State Design Pattern: Customer loyalty status is managed through a CustomerState interface with SilverState and GoldState implementations, encapsulating status-specific behavior instead of relying on conditional logic

Purchase Handling: A PurchaseResult class captures purchase outcomes (total cost, remaining points, updated status) for clean data handling between the backend and UI




Process:

Designed a UML use-case diagram to define owner and customer interactions with the system

Designed a UML class diagram to plan classes, attributes, methods, and relationships, including the State Design Pattern for loyalty status

Implemented the system in Java Swing using NetBeans, refining the original design (e.g., converting screens to panels, adding backend and result-handling classes) during development

Tested full owner and customer workflows, including login, book/customer management, and point-based purchasing




Tools Used:

Java, Swing, NetBeans, UML (Visual Paradigm)
