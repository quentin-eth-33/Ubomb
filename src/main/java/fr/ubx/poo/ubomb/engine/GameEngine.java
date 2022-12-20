/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.engine;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Level;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.view.*;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public final class GameEngine {

    private static AnimationTimer gameLoop;
    private final Game game;
    private final Player player;

    private final List<Sprite> sprites = new LinkedList<>();
    private final Set<Sprite> cleanUpSprites = new HashSet<>();
    private final Stage stage;

    private Scene scenes[] ;
    private StatusBar statusBar;
    private Pane[] layer;
    private Input input;

    public GameEngine(Game game, final Stage stage) {
        this.stage = stage;
        this.game = game;
        this.player = game.player();
        initialize();
        buildAndSetGameLoop();
    }

    private void initialize() {
        layer = new Pane[game.getNbLevels()];
        scenes = new Scene[game.getNbLevels()];
        for (int i = 1; i <= game.getNbLevels(); i++) {
            layer[i-1] = new Pane();
            Group root = new Group();

            int height = game.grid(i).height();
            int width = game.grid(i).width();
            int sceneWidth = width * ImageResource.size;
            int sceneHeight = height * ImageResource.size;

            scenes[i-1] = new Scene(root, sceneWidth, sceneHeight + StatusBar.height);
            scenes[i-1].getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

            if (i==1) {
                stage.setScene(scenes[0]);
                stage.setResizable(false);
                stage.sizeToScene();
                stage.hide();
                stage.show();
                input = new Input(scenes[0]);
                statusBar = new StatusBar(root, sceneWidth, sceneHeight, game);
            }

            root.getChildren().add(layer[i-1]);
            statusBar = new StatusBar(root, sceneWidth, sceneHeight, game);

            // Create sprites
            for (var decor : game.grid(i).values()) {
                if (!(decor instanceof Monster)) {
                    sprites.add(SpriteFactory.create(layer[i-1], decor));
                    decor.setModified(true);
                }
            }

            sprites.add(new SpritePlayer(layer[i-1], player));
            for (Monster monster : ((Level)this.game.grid(i)).getMonsters()) {
                sprites.add(new SpriteMonster(layer[i-1], monster));
                System.out.println("Monster: " + monster);
            }
        }
    }

    void buildAndSetGameLoop() {
        gameLoop = new AnimationTimer() {
            public void handle(long now) {
                // Check keyboard actions
                processInput(now);

                // Do actions
                update(now);
                createNewBombs(now);
                checkCollision(now);
                checkExplosions();
                //moveMonster();

                // Graphic update
                cleanupSprites();
                render();
                statusBar.update(game);
            }
        };
    }

    private void checkExplosions() {
        // Check explosions of bombs
    }

    private void moveMonster(){
        Direction direction;
        for(int i =1 ; i<=this.game.getNbLevels(); i++ )
        for(Monster monster : ((Level)this.game.grid(i)).getMonsters()){
            direction = Direction.random();
            monster.requestMove(direction);
        }
    }

    private void animateExplosion(Position src, Position dst) {
        ImageView explosion = new ImageView(ImageResource.EXPLOSION.getImage());
        TranslateTransition tt = new TranslateTransition(Duration.millis(200), explosion);
        tt.setFromX(src.x() * Sprite.size);
        tt.setFromY(src.y() * Sprite.size);
        tt.setToX(dst.x() * Sprite.size);
        tt.setToY(dst.y() * Sprite.size);
        tt.setOnFinished(e -> {
            layer[player.getInLevel()].getChildren().remove(explosion);
        });
        layer[player.getInLevel()].getChildren().add(explosion);
        tt.play();
    }

    private void createNewBombs(long now) {
        // Create a new Bomb is needed
    }

    private void checkCollision(long now) {
        // Check a collision between a monster and the player
    }

    private void processInput(long now) {
        if (input.isExit()) {
            gameLoop.stop();
            Platform.exit();
            System.exit(0);
        } else if (input.isMoveDown()) {
            player.requestMove(Direction.DOWN);
            moveMonster();
        } else if (input.isMoveLeft()) {
            player.requestMove(Direction.LEFT);
            moveMonster();
        } else if (input.isMoveRight()) {
            player.requestMove(Direction.RIGHT);
            moveMonster();
        } else if (input.isMoveUp()) {
            player.requestMove(Direction.UP);
            moveMonster();
        } else if (input.isKey()) {
            player.requestOpen();
        }
        input.clear();
    }

    private void showMessage(String msg, Color color) {
        Text waitingForKey = new Text(msg);
        waitingForKey.setTextAlignment(TextAlignment.CENTER);
        waitingForKey.setFont(new Font(60));
        waitingForKey.setFill(color);
        StackPane root = new StackPane();
        root.getChildren().add(waitingForKey);
        Scene scene = new Scene(root, 400, 200, Color.WHITE);
        stage.setScene(scene);
        input = new Input(scene);
        stage.show();
        new AnimationTimer() {
            public void handle(long now) {
                processInput(now);
            }
        }.start();
    }


    private void update(long now) {
        player.update(now);
        for(int i =1 ; i<=this.game.getNbLevels(); i++ )
        for(Monster monster : ((Level)this.game.grid(i)).getMonsters()){
            monster.update(now);
        }
        if(player.getInLevel()==2) {
            stage.setScene(scenes[1]);
            input = new Input(scenes[1]);
        }

        if (player.getLives() <= 0) {
            gameLoop.stop();
            showMessage("Perdu!", Color.RED);
        }
        else if(player.getPrincessFound() == true){
            gameLoop.stop();
            showMessage("Gagné!", Color.GREEN);
        }
    }

    public void cleanupSprites() {
        sprites.forEach(sprite -> {
            if (sprite.getGameObject().isDeleted()) {
                game.grid(this.player.getInLevel()).remove(sprite.getPosition()); //pas sur de player.getInlevel()
                cleanUpSprites.add(sprite);
            }
        });
        cleanUpSprites.forEach(Sprite::remove);
        sprites.removeAll(cleanUpSprites);
        cleanUpSprites.clear();
    }

    private void render() {
        sprites.forEach(Sprite::render);
    }

    public void start() {
        gameLoop.start();
    }
}