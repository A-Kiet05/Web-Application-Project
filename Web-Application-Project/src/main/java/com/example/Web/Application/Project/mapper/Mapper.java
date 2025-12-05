package com.example.Web.Application.Project.mapper;

public interface Mapper<A,B> {
    

    B mapTo ( A a );
    
    A mapFrom (B b );
}
