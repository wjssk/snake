package com.example.check;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int ROWS = 20;
    private static final int COLUMNS = 20;
    private static final int SQUARE_SIZE = WIDTH / ROWS;
    private static final String[] FOOD_IMG = new String[]{"/img/burger.png", "/img/cake-slice.png", "/img/cotton-candy.png", "/img/cotton-candy (1).png", "/img/cupcake.png", "/img/donut.png", "/img/gingerbread-man.png", "/img/pancakes.png", "/img/pizza.png", "/img/skewer.png"};
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
    private int score = 0;
    private Button playAgain;
    private boolean playAgainAdded = false;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Snake");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if(code == KeyCode.RIGHT || code == KeyCode.D) {
                    if(currDirection != LEFT) {
                        currDirection = RIGHT;
                    }
                } else if(code == KeyCode.LEFT || code == KeyCode.A) {
                    if(currDirection != RIGHT) {
                        currDirection = LEFT;
                    }
                } else if(code == KeyCode.DOWN || code == KeyCode.S) {
                    if(currDirection != UP) {
                        currDirection = DOWN;
                    }
                } else if(code == KeyCode.UP || code == KeyCode.W) {
                    if(currDirection != DOWN) {
                        currDirection = UP;
                    }
                }
            }
        });
        for(int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);
        generateFood();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(115), e->run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run() {
        if(gameOver) {
            this.gc.setFill(Color.web("#cf0000"));
            this.gc.setFont(new Font("Courier", 70));
            this.gc.fillText("Game Over", WIDTH/4.5, HEIGHT/2);
            if(!playAgainAdded) {
                addPlayAgainButton();
            } else {
                playAgain.setVisible(true);
            }
            return;
        }

        drawBackground(this.gc);
        drawFood(this.gc);
        drawSnake((this.gc));
        drawScore();

        for(int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        switch(currDirection) {
            case 0:
                moveRight();
                break;
            case 1:
                moveLeft();
                break;
            case 2:
                moveUp();
                break;
            case 3:
                moveDown();
                break;
        }

        eatFood();
        gameOver();
    }

    private void addPlayAgainButton() {
        playAgain = new Button("Play Again");
        playAgain.setTranslateX(WIDTH/2);
        playAgain.setTranslateY(HEIGHT/2);
        playAgain.setOnAction(e -> restartGame());
        ((Group) gc.getCanvas().getParent()).getChildren().add(playAgain);
        playAgainAdded = true;
    }

    private void drawBackground(GraphicsContext gc) {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                if((i + j) % 2 == 0) {
                    gc.setFill(Color.web("ffb8da"));
                } else {
                    gc.setFill(Color.web("fca4d0"));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    private void generateFood() {
        boolean isFoodOnSnake;
        do {
            foodX = (int) (Math.random() * ROWS);
            foodY = (int) (Math.random() * COLUMNS);

            isFoodOnSnake = false;
            for (Point snake : snakeBody) {
                if (snake.getX() == foodX && snake.getY() == foodY) {
                    isFoodOnSnake = true;
                    break;
                }
            }
        } while (isFoodOnSnake);

        foodImage = new Image(getClass().getResource(FOOD_IMG[(int) (Math.random() * FOOD_IMG.length)]).toString());
    }

    private void drawFood(GraphicsContext gc) {
        gc.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void drawSnake(GraphicsContext gc) {
        gc.setFill(Color.web("80183b"));
        gc.fillRoundRect(snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE,SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);

        for(int i = 1; i < snakeBody.size(); i++) {
            gc.fillRoundRect(snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE,SQUARE_SIZE - 1, SQUARE_SIZE - 1, 20, 20);
        }
    }

    private void moveRight() {
        snakeHead.x++;
    }

    private void moveLeft() {
        snakeHead.x--;
    }

    private void moveUp() {
        snakeHead.y--;
    }

    private void moveDown() {
        snakeHead.y++;
    }

    public void gameOver() {
        if(snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
            this.gameOver = true;
        }

        for(int i = 1; i < snakeBody.size(); i++) {
            if(snakeHead.x == snakeBody.get(i).getX() && snakeHead.y == snakeBody.get(i).getY()) {
                this.gameOver = true;
                break;
            }
        }
    }

    private void eatFood() {
        if(snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
            snakeBody.add(new Point(-1, -1));
            generateFood();
            score += 1;
        }
    }

    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Courier", 35));
        gc.fillText("Score: " + score, 10,35);
    }

    private void restartGame() {
        gameOver = false;
        score = 0;
        currDirection = RIGHT;
        snakeBody.clear();
        for(int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);
        generateFood();
        playAgain.setVisible(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
