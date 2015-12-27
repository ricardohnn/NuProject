package com.rdzero.nuproject.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by ricardohnn on 2015-12-20.
 */
@Database(name = NuProjDatabase.NAME, version = NuProjDatabase.VERSION)
public class NuProjDatabase{

    // Database Version
    public static final int VERSION  = 1;

    // Database Name
    public static final String NAME = "nuProjectDatabase";

}