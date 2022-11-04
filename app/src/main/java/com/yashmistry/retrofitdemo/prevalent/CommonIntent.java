package com.yashmistry.retrofitdemo.prevalent;

import android.content.Context;
import android.content.Intent;

public class CommonIntent {
    public CommonIntent(Context context , Class<?> myclass) {
        Intent intent  = new Intent(context,myclass);
        context.startActivity(intent);

    }
}
