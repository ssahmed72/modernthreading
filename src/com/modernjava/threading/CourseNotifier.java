package com.modernjava.threading;

public class CourseNotifier {
    public static void main(String[] args) throws InterruptedException {
        final Course course = new Course("Java Multithreaded Programming");

        //create three thread two for the students waiting for notification
        //one for the instructor who is writing the course

        new Thread (() ->{
            synchronized (course){
                System.out.println(Thread.currentThread().getName() + " is waiting for the course:"
                        + course.getTitle());
                try {
                    course.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " the course: " + course.getTitle()
                 + " is now completed");
                course.notify();
            }


        }, "StudentA").start();



        new Thread (() ->{
            synchronized (course){
                System.out.println(Thread.currentThread().getName() + " is waiting for the course: "
                 + course.getTitle());
                try {
                    course.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " the course: " + course.getTitle()
                 + " is now completed");
                course.notify();
            }

        }, "StudentB").start();

        Thread.sleep(200);
        new Thread (() ->{
            synchronized (course){
                System.out.println(Thread.currentThread().getName() + " is starting a new course: "
                        + course.getTitle());
                try {
                    Thread.sleep(4000);
                    course.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "Syed Ahmed").start();



    }
}

class Course {
    private String title;
    private boolean completed;

    public Course (String title){
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
