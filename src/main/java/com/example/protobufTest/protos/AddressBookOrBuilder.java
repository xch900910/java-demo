// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: addressbook.proto

package com.example.protobufTest.protos;

public interface AddressBookOrBuilder extends
        // @@protoc_insertion_point(interface_extends:tutorial.AddressBook)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .tutorial.Person people = 1;</code>
     */
    java.util.List<com.example.protobufTest.protos.Person>
    getPeopleList();

    /**
     * <code>repeated .tutorial.Person people = 1;</code>
     */
    com.example.protobufTest.protos.Person getPeople(int index);

    /**
     * <code>repeated .tutorial.Person people = 1;</code>
     */
    int getPeopleCount();

    /**
     * <code>repeated .tutorial.Person people = 1;</code>
     */
    java.util.List<? extends com.example.protobufTest.protos.PersonOrBuilder>
    getPeopleOrBuilderList();

    /**
     * <code>repeated .tutorial.Person people = 1;</code>
     */
    com.example.protobufTest.protos.PersonOrBuilder getPeopleOrBuilder(
            int index);
}
