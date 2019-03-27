package com.lzy.bulletproject.uhome.child.dagger2;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * @author bullet
 * @date 2019\3\2 0002.
 */

@Module
public class PersonA {

    private String name ;


    public PersonA(String name) {
        this.name = name;

    }


}
