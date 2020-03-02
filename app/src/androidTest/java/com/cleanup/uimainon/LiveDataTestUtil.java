package com.cleanup.uimainon;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;

import android.support.annotation.Nullable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

//classe utilitaire fournie par Google  nous permettant de lancer
// plus facilement des méthodes retournant des valeurs de type liveData
// et surtout à bloquer l'exécution du test tant que le résultat n'est pas retourné.
public class LiveDataTestUtil {


    public static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        //CountDownLatch est une classe qui aide à travailler avec la synchronisation de plusieurs threads.
        //prend en paramètre de son constructeur le nombre de threads qui doivent être
        // exécutés afin que la tâche comprise dans un autre thread puisse être invoquée
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }




}
