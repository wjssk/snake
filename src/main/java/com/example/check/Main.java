package com.example.check;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int ROWS = 20;
    private static final int COLUMNS = 20;
    private static final int SQUARE_SIZE = WIDTH / ROWS;
    private static final String[] FOOD_IMG = new String[]{"/resources/img/burger.png", "/resources/img/cake-slice.png", "/resources/img/cotton-candy.png", "/resources/img/cotton-candy (1).png", "/resources/img/cupcake.png", "/resources/img/donut.png", "/resources/img/gingerbread-man.png", "/resources/img/pancakes.png", "/resources/img/pizza.png", "/resources/img/skewer.png", };

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private GraphicsContext gc;
    private List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private Image foodImage;

    private int foodX;
    private int foodY;
    private boolean gameOver;
    private int currDirection;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Snake");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}