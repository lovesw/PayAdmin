package com.pay.data.utils;

import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.lang.Console;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @createTime: 2018/3/2
 * @author: HingLo
 * @description:
 */
public class FileUtilsss {
    private static void hello() {
        File file = new File("d:/logs/");
        WatchMonitor.createAll(file, new SimpleWatcher() {
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Console.log("EVENT modify");
            }

        }).start();
    }

    public static void main(String[] args) {

    }
}
