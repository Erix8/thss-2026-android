package com.example.threads;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ProAndCon test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView.setText("");

        test = new ProAndCon(5,20);
    }


    public void onProducerClick(View v){
        test.addProducer();
    }

    public void onConsumerClick(View v){
        test.addConsumer();
    }

    class ProAndCon {
        private final List<Integer> queue;
        int count = 0;
        int stop;
        int max;

        ProAndCon(int length, int stop){
            queue = new ArrayList<>();
            max=length;
            this.stop = stop;
        }

        class producer implements Runnable{
            int ms,n;
            producer(int ms,int n){
                this.ms = ms;
                this.n=n;
            }
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                try {
                    while (count<stop) {
                        produce();
                        Thread.sleep(ms);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            void produce() throws Exception {
                List<String> logs = new ArrayList<>();
                synchronized (queue){
                    while (queue.size()==max && count<stop){
                        queue.wait();
                    }
                    if (count>=stop){
                        queue.notifyAll();
                        return;
                    }
                    int capacity = max - queue.size();
                    int toProduce = Math.min(n, capacity);
                    toProduce = Math.min(toProduce, stop - count);
                    if (toProduce<=0){
                        queue.notifyAll();
                        return;
                    }
                    String threadName = Thread.currentThread().getName();
                    for (int i = 0; i < toProduce; i++) {
                        int id = ++count;
                        queue.add(id);
                        logs.add(threadName + " Produce: " + id + " Remain: " + queue.size() + "\n");
                    }
                    appendLogs(logs);
                    queue.notifyAll();
                }
            }
        }

        class consumer implements  Runnable{
            int ms;
            consumer(int ms){
                this.ms = ms;
            }
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                try {
                    while (count<stop) {
                        consume();
                        Thread.sleep(ms);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            void consume() throws Exception{
                String log;
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        if (count>=stop){
                            queue.notifyAll();
                            return;
                        }
                        queue.wait();
                    }
                    int id = queue.remove(0);
                    log = Thread.currentThread().getName() + " Consume: " + id + " Remain: " + queue.size() + "\n";
                    appendLogs(Collections.singletonList(log));
                    queue.notifyAll();
                }
            }
        }

        void addProducer(){
            new Thread(new producer(2000, 2)).start();
        }

        void addConsumer(){
            new Thread(new consumer(1000)).start();
        }

        private void appendLogs(List<String> logs){
            if (logs==null || logs.isEmpty()){
                return;
            }
            MainActivity.this.runOnUiThread(() -> {
                for (String log : logs) {
                    textView.append(log);
                }
            });
        }
    }
}
