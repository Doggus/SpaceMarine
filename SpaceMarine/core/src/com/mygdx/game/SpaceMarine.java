package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;
import java.util.Random;

public class SpaceMarine extends ApplicationAdapter implements ApplicationListener {

	private OrthographicCamera camera;
	private SpriteBatch batch;

	//animation and player
	private float elapsedTime;
	private Animate animate;
	private Animate bulletHit;
	private Animate Skullenemies;
    private Animate Golemenemies;
    private Animate Slimeenemies;
	private Texture idleD;
	private Texture idleU;
	private Texture idleL;
	private Texture idleR;
	private Texture movingUp;
	private Texture movingDown;
	private Texture movingLeft;
	private Texture movingRight;
	private Player player;


	//guns and bullets
	private int guns;
	private float bulletHitX;
	private float bulletHitY;
	private int boom = 0;

	//music and sound effects
	private Music music;
	private Sound gunShot_handgun;
	private Sound gunShot_shotgun;
	private Sound gunShot_rpg;
	private Sound explosion;
	private Sound hurt;
	private Sound armorDamage;

	//directions
	enum Axis { X, Y};
	enum Direction { U, D, L, R};
	private Direction direction;

	//Relevant lists
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private ArrayList<Tiles> tiles = new ArrayList<Tiles>();
	private ArrayList<OtherTiles> otherTiles = new ArrayList<OtherTiles>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<OtherTiles> weapons = new ArrayList<OtherTiles>();
    private ArrayList<OtherTiles> armor = new ArrayList<OtherTiles>();
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    //Random
    Random random = new Random();

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,902,675);
		batch = new SpriteBatch();

		//Music and sound effects
		music = Gdx.audio.newMusic(Gdx.files.internal("data/Thunderstep.mp3"));
		gunShot_handgun = Gdx.audio.newSound(Gdx.files.internal("data/Handgun.wav"));
		gunShot_shotgun = Gdx.audio.newSound(Gdx.files.internal("data/Shotgun.wav"));
		gunShot_rpg = Gdx.audio.newSound(Gdx.files.internal("data/Rocket_launcher.wav"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("data/explosion.mp3"));
        hurt = Gdx.audio.newSound(Gdx.files.internal("data/hurt.wav"));
        armorDamage = Gdx.audio.newSound(Gdx.files.internal("data/armorDamage.wav"));

		// play(); //plays background music

        //Textures for animation
		idleD = new Texture("player1_idleD.png");
		idleU = new Texture("player1_idleU.png");
		idleL = new Texture("player1_idleL.png");
		idleR = new Texture("player1_idleR.png");
		movingUp = new Texture("Player1_walkUp.png");
		movingDown = new Texture("Player1_walkDown.png");
		movingLeft = new Texture("Player1_walkLeft.png");
		movingRight = new Texture("Player1_walkRight.png");

		//adding entities to lists
		objects.add(new Player(100,100,bullets));
		player = (Player)objects.get(0); //player is first game object (in list)

        enemies.add(new Skull(500,200,16,28)); //enemy (skull)
        enemies.add(new Skull(700,400,16,28)); //enemy (skull)
        enemies.add(new Skull(300,500,16,28)); //enemy (skull)
        enemies.add(new Golem(400,400,29,29)); //enemy (golem)
        enemies.add(new Golem(600,200,29,29)); //enemy (golem)
        enemies.add(new Slime(500,350,15,12)); //enemy (slime)
        enemies.add(new Slime(200,500,15,12)); //enemy (slime)

        direction = Direction.D; //direction starts down

		//Intangible Tiles
		int t1 = 0;
		int t2;
		for (int i = 0; i < 28; i++){
			t2=0;
			for(int j = 0; j < 17; j++)
			{
				otherTiles.add(new OtherTiles(t1,t2,new Texture("tile_floor.png")));
				t2+=31;
			}

			t1+=31;
		}

		otherTiles.add(new OtherTiles(0,575,new Texture("banner.png")));
        otherTiles.add(new OtherTiles(450,625,new Texture("Shield.png")));
        otherTiles.add(new OtherTiles(450,580,new Texture("HealthCross.png")));
        otherTiles.add(new OtherTiles(500,575,new Texture("HealthBar_full.png")));
        weapons.add(new OtherTiles(0,580,new Texture("Handgun.png")));
        armor.add(new OtherTiles(500,625,new Texture("ArmorBar_full.png")));

		//Tangible Tiles
		int c = 64;
		for (int i = 0; i < 13; i++){
			tiles.add(new Tiles(c,-2,new Texture("tile_wall_back.png")));
			c+=64;
		}

		int c1 = 60;
		for (int i = 0; i < 8; i++){
			tiles.add(new Tiles(859,c1,new Texture("tile_wall_right.png")));
			c1+=64;
		}

		int c2 = 60;
		for (int i = 0; i < 8; i++){
			tiles.add(new Tiles(0,c2,new Texture("tile_wall_left.png")));
			c2+=64;
		}

		int c3 = 63;
		for (int i = 0; i < 13; i++){
			tiles.add(new Tiles(c3,529,new Texture("tile_wall_front.png")));
			c3+=64;
		}

		tiles.add(new Tiles(844,-3,new Texture("tile_corner_bottomRight.png")));
		tiles.add(new Tiles(0,-2,new Texture("tile_corner_bottomLeft.png")));
		tiles.add(new Tiles(-1,513,new Texture("tile_corner_topLeft.png")));
		tiles.add(new Tiles(843,515,new Texture("tile_corner_topRight.png")));

		tiles.add(new Tiles(625,380,new Texture("tile_statue.png")));
		tiles.add(new Tiles(235,380,new Texture("tile_statue.png")));
		tiles.add(new Tiles(625,180,new Texture("tile_statue.png")));
		tiles.add(new Tiles(235,180,new Texture("tile_statue.png")));

		//NB for animation
		animate = new Animate(new Texture("player1_idleD.png"),38,43,true); //player
		bulletHit = new Animate(new Texture("bulletHit.png"),24,20,true); //bullet hit
		Skullenemies = new Animate(new Texture("Flameskull_down.png"),16,25,true); //Skull animation
        Golemenemies = new Animate(new Texture("Golem_down.png"),31,29,true); //Golem animation
        Slimeenemies = new Animate(new Texture("Slime.png"),15,12,true); //Slime animation


		//weapons
		guns = 1;

	}

	public void moveEntity(GameObject g, float newX, float newY) {
		// just check x collisions keep y the same
		moveEntityInAxis(g, Axis.X, newX, g.getY());
		// just check y collisions keep x the same
		moveEntityInAxis(g, Axis.Y, g.getX(), newY);
	}

	public void moveEntityInAxis(GameObject g, Axis axis, float newX, float newY) {

		// determine axis direction

		if(axis == Axis.Y) {
			if(newY - g.getY() > 0) {
				direction = Direction.U;
			}

			if(newY - g.getY() < 0){
				direction = Direction.D;
			}
		}

		if(axis == Axis.X)
		{
			if(newX - g.getX() < 0)
			{
				direction = Direction.L;
			}

			if(newX - g.getX() > 0)
			{
				direction = Direction.R;
			}

		}


		if(!TileCollision(g, direction, newX, newY)) {

            if(!Collision(g,direction,newX,newY)) {

                // full move with no collision (can still move if enemy is touching)
                g.move(newX, newY);

                if(EnemyCollision(g,direction,newX,newY)) {

                    System.out.println("Enemy Collision");

                   if (player.isAlive())
                   {
                       player.loseArmor();

                       if(player.getArmor()<=0)
                       {
                           player.loseHealth(1); //for now (later items will also be game objects)
                       }
                   }
               }
            }
            else
            {
                System.out.println("Collision GameObject");
            }
		}
		else
        {
			System.out.println("Tile collision");
		}

		// else collision occurs
	}

	//Collision with gameObjects
	public boolean Collision(GameObject g1, Direction direction, float newX, float newY) {
        boolean collision = false;

        for(int i = 0; i < objects.size(); i++) {
            GameObject g2 = objects.get(i);

            // we don't want to check for collisions between the same entity
            if(g1 != g2) {
                // axis aligned rectangle rectangle collision detection
                if(newX < g2.getX() + g2.getWidth() && g2.getX() < newX + g1.getWidth() &&
                        newY < g2.getY() + g2.getHeight() && g2.getY() < newY + g1.getHeight())
                {
                    collision = true;
                }
            }
        }

        return collision;
    }

    //colliding with tiles
    public boolean TileCollision(GameObject g1, Direction direction, float newX, float newY) {
        boolean collision = false;

        //collision with tangible tiles
        for(int i = 0; i < tiles.size(); i++) {
            Tiles t = tiles.get(i);

            // axis aligned rectangle rectangle collision detection
            if(newX < t.getX() + t.getWidth() && t.getX() < newX + g1.getWidth() &&
                    newY < t.getY() + t.getHeight() && t.getY() < newY + g1.getHeight())
            {
                collision = true;
            }
        }

        return collision;
    }

    //enemies colliding with gameObjects
    public boolean EnemyCollision(GameObject g1, Direction direction, float newX, float newY) {
        boolean collision = false;

        //collision with tangible tiles
        for(int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);

            // axis aligned rectangle rectangle collision detection
            if(newX < e.getX() + e.getWidth() && e.getX() < newX + g1.getWidth() &&
                    newY < e.getY() + e.getHeight() && e.getY() < newY + g1.getHeight())
            {
                collision = true;
            }
        }

        return collision;
    }

    //bullets colliding with enemies
    public boolean bulletCollision(Bullet b, Enemy e, float newX, float newY) {

	    boolean collide = false;
	    //for(int i = 0; i < enemies.size(); i++) {
           // Enemy e  = enemies.get(i);

            // axis aligned rectangle rectangle collision detection
            if(newX < e.getX() + e.getWidth() && e.getX() < newX + b.getWidth() &&
                    newY < e.getY() + e.getHeight() && e.getY() < newY + b.getHeight())
            {
                collide =true;
            }
        //}
        return collide;
    }

    //Animation directions
	public void animation()
	{

	    //walking animation swaps
        if (direction == Direction.U) {
            player.setDirection(Direction.U);
            animate.SwapAnimation(movingUp, 46, 46, true);

            for(Enemy e : enemies)
            {
                e.setDirection(Direction.U);
            }
            Skullenemies.SwapAnimation(new Texture("Flameskull_up.png"), 16, 25, true);
            Golemenemies.SwapAnimation(new Texture("Golem_up.png"), 31, 29, true);
        }

        if (direction == Direction.D) {
            player.setDirection(Direction.D);
            animate.SwapAnimation(movingDown, 46, 46, true);

            for(Enemy e : enemies)
            {
                e.setDirection(Direction.D);
            }
            Skullenemies.SwapAnimation(new Texture("Flameskull_down.png"), 16, 25, true);
            Golemenemies.SwapAnimation(new Texture("Golem_down.png"), 31, 29, true);

        }

        if (direction == Direction.L) {
            player.setDirection(Direction.L);
            animate.SwapAnimation(movingLeft, 46, 46, false);

            for(Enemy e : enemies)
            {
                e.setDirection(Direction.L);
            }
            Skullenemies.SwapAnimation(new Texture("Flameskull_left.png"), 16, 28, true);
            Golemenemies.SwapAnimation(new Texture("Golem_left.png"), 29, 29, true);
        }

        if (direction == Direction.R) {
            player.setDirection(Direction.R);
            animate.SwapAnimation(movingRight, 46, 46, false);

            for(Enemy e : enemies)
            {
                e.setDirection(Direction.R);
            }
            Skullenemies.SwapAnimation(new Texture("Flameskull_right.png"), 16, 28, true);
            Golemenemies.SwapAnimation(new Texture("Golem_right.png"), 29, 29, true);
        }

        //Idle animation swaps
        if (player.getDY() == 0 && player.getDX() == 0 && direction == Direction.U) {
            animate.SwapAnimation(idleU, 38, 43, true);
        }

        if (player.getDY() == 0 && player.getDX() == 0 && direction == Direction.D) {
            animate.SwapAnimation(idleD, 38, 43, true);
        }

        if (player.getDY() == 0 && player.getDX() == 0 && direction == Direction.L) {
            animate.SwapAnimation(idleL, 43, 38, false);
        }

        if (player.getDY() == 0 && player.getDX() == 0 && direction == Direction.R) {
            animate.SwapAnimation(idleR, 43, 38, false);
        }
    }

	@Override
	public void render () {

		float delta = Gdx.graphics.getDeltaTime();

        //bullets hitting enemies
        for(int i = 0;i<bullets.size();i++)
        {
            Bullet bill = bullets.get(i);
            for(int j =0; j<enemies.size();j++) {
                Enemy e = enemies.get(j);
                if (bulletCollision(bill, e, bill.getX(), bill.getY())) {
                    System.out.println("hit");

                    //bullets.remove(i); //causes errors
                    e.loseHealth(bill.getDamage());
                }
            }
        }

        // update all elements in the game objects list
		for(int i = objects.size() - 1; i >= 0; i--) {
			GameObject g = objects.get(i);
			// update object based on input/ai/physics etc
			// this is where we determine the change in position
			g.update(delta);
			// now we try move the entity on the map and check for collisions
			moveEntity(g, g.getX() + g.getDX(), g.getY() + g.getDY());

        }

         //updates enemy movement and health/death and adds new enemies
         for(int i = 0; i<enemies.size();i++)
         {
            Enemy e = enemies.get(i);
            e.update(delta);
             if(e.getHealth()<=0)
             {
                 enemies.remove(i);
                 e.alive=false;

                 //when enemy dies add a new one
                 int type = random.nextInt(3);
                 int posx = random.nextInt(600)+100;
                 int posy = random.nextInt(300)+100;


                 if(type==0){
                     enemies.add(new Skull(posx, posy, 16, 28));
                 }
                 else if(type==1)
                 {
                     enemies.add(new Golem(posx,posy,29,29));
                 }
                 else if(type==2)
                 {
                     enemies.add(new Slime(posx,posy,15,12));
                 }

             }
         }

        //A.I (basic following)
       for(Enemy e : enemies)
       {
           e.follow(player.getX(),player.getY());
       }


        //Armor bar
        if(player.isAlive()) {
            Armor();
        }

        //Health bar (Health bar must be last item in list for this to work)
        if(player.isAlive()) {
            Health();
        }

        //Changing Weapons
        ChangeWeapon();

        //Shooting weapons
        if(player.isAlive()) {
            ShootWeapon();
            Minigun();
        }

		//updates and removes bullets after certain amount of time (also controls firing speed and explosions)
        UpdateBulletAndExplosion(delta);

        //animation
        if(player.isAlive()) {
            animation();
        }

        //draw
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		elapsedTime += delta;
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//Intangible Tiles
		for(OtherTiles t : otherTiles)
		{
			t.draw(batch);
		}

		//weapon display
        for(OtherTiles w : weapons)
        {
            w.draw(batch);
        }

        //Armor bar
        for(OtherTiles a : armor)
        {
            a.draw(batch);
        }

		//Tangible Tiles
		for(Tiles t : tiles)
		{
			t.draw(batch);
		}

		//All game objects
		for(GameObject g : objects)
		{
			//normal drawing
			g.draw(batch);
		}

		//bullets
		for(Bullet b : bullets)
		{
			b.draw(batch);
		}

		//game over
        if(!player.isAlive())
        {
            batch.draw(new Texture("GameOver.png"),-50,-50);
        }

		//animation drawing (drawing player and enemies)
        if(player.isAlive())
        {
            batch.draw((TextureRegion) animate.animatePlayer().getKeyFrame(elapsedTime, true), player.getX(), player.getY());
            batch.draw((TextureRegion) bulletHit.bulletHit().getKeyFrame(elapsedTime, true), bulletHitX, bulletHitY);
        }
        if(!enemies.isEmpty() && player.isAlive()) {

		    for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                if(e.getType()==1) {
                    batch.draw((TextureRegion) Skullenemies.animateEnemy().getKeyFrame(elapsedTime, true), e.getX(), e.getY());
                }

                if(e.getType()==2)
                {
                    batch.draw((TextureRegion) Golemenemies.animateEnemy().getKeyFrame(elapsedTime, true), e.getX(), e.getY());
                }

                if(e.getType()==3)
                {
                    batch.draw((TextureRegion) Slimeenemies.animateEnemy().getKeyFrame(elapsedTime, true), e.getX(), e.getY());
                }

            }
        }

        batch.end();

		//updateCamera();
	}

	@Override
	public void dispose () {
		batch.dispose();

		music.dispose();
		gunShot_shotgun.dispose();
		gunShot_handgun.dispose();
        gunShot_rpg.dispose();
        explosion.dispose();
        armorDamage.dispose();
        hurt.dispose();
	}

	public void play()
	{
		music.setLooping(true);
		music.setVolume(0.2f);
		music.play();
	}

	//health
	public void Health()
    {
        if (player.getArmor() <= 0) {
            if (player.getHealth() <= 75 && player.getHealth() > 50) {
                otherTiles.remove(otherTiles.size() - 1);
                otherTiles.add(new OtherTiles(500, 575, new Texture("HealthBar_3q.png")));

                //temp fix
                if(player.getHealth()==75)
                {
                    long vf = hurt.play();
                    hurt.setVolume(vf, 0.2f);
                    player.loseHealth(1);
                }
            }

            if (player.getHealth() <= 50 && player.getHealth() > 25) {
                otherTiles.remove(otherTiles.size() - 1);
                otherTiles.add(new OtherTiles(500, 575, new Texture("HealthBar_half.png")));

                //temp fix
                if(player.getHealth()==50)
                {
                    long vf = hurt.play();
                    hurt.setVolume(vf, 0.2f);
                    player.loseHealth(1);
                }
            }

            if (player.getHealth() <= 25 && player.getHealth() > 0) {
                otherTiles.remove(otherTiles.size() - 1);
                otherTiles.add(new OtherTiles(500, 575, new Texture("HealthBar_1q.png")));

                //temp fix
                if(player.getHealth()==25)
                {
                    long vf = hurt.play();
                    hurt.setVolume(vf, 0.2f);
                    player.loseHealth(1);
                }
            }

            if (player.getHealth() <= 0) {
                otherTiles.remove(otherTiles.size() - 1);
                otherTiles.add(new OtherTiles(500, 575, new Texture("HealthBar_empty.png")));

                //temp fix
                if(player.getHealth()==0)
                {
                    long vf = hurt.play();
                    hurt.setVolume(vf, 0.2f);
                    player.loseHealth(1);
                }

                //delete player
                player.alive = false;
                objects.remove(0); //player is first item in gameObject list
            }
        }
    }

    //armor
    public void Armor()
    {
        if (player.getArmor() <= 150 && player.getArmor() > 100) {
            armor.remove(0);
            armor.add(new OtherTiles(500, 625, new Texture("ArmorBar_3q.png")));

            //temp fix
            if(player.getArmor()==150)
            {
                long vf = armorDamage.play();
                armorDamage.setVolume(vf, 0.1f);
                player.loseArmor();
            }
        }

        if (player.getArmor() <= 100 && player.getArmor() > 50) {
            armor.remove(0);
            armor.add(new OtherTiles(500, 625, new Texture("ArmorBar_half.png")));

            //temp fix
            if(player.getArmor()==100)
            {
                long vf = armorDamage.play();
                armorDamage.setVolume(vf, 0.1f);
                player.loseArmor();
            }

        }

        if (player.getArmor() <= 50 && player.getArmor() > 0) {
            armor.remove(0);
            armor.add(new OtherTiles(500, 625, new Texture("ArmorBar_1q.png")));

            //temp fix
            if(player.getArmor()==50)
            {
                long vf = armorDamage.play();
                armorDamage.setVolume(vf, 0.1f);
                player.loseArmor();
            }

        }

        if (player.getArmor() <= 0) {
            armor.remove(0);
            armor.add(new OtherTiles(500, 625, new Texture("ArmorBar_empty.png")));

            //temp fix
            if(player.getArmor()==0)
            {
                long vf = armorDamage.play();
                armorDamage.setVolume(vf, 0.1f);
                player.loseArmor();
            }

        }
    }

    //changing weapons
    public void ChangeWeapon()
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){

            if(guns!=1)
            {
                weapons.remove(0);
                weapons.add(new OtherTiles(0,580,new Texture("Handgun.png")));
                guns=1;
            }

        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
            if(guns!=2){
                weapons.remove(0);
                weapons.add(new OtherTiles(0,570,new Texture("Shotgun.png")));
                guns=2;
            }

        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){

            if(guns!=3)
            {
                weapons.remove(0);
                weapons.add(new OtherTiles(0,580,new Texture("RPG.png")));
                guns=3;
            }

        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){

            if(guns!=4)
            {
                weapons.remove(0);
                weapons.add(new OtherTiles(0,570,new Texture("Minigun.png")));
                guns=4;
            }

        }
    }

    //shooting first 3 guns
    public void ShootWeapon()
    {
        // Invert the the y to accommodate difference in coordinate systems
        if (Gdx.input.justTouched()) {

            if (guns == 1) //pistol
            {
                if (bullets.size() > 6) { //fire rate
                    return;
                }

                ////plays sound effect (and sets volume lower)
                if (bullets.size() < 6) {
                    long vf = gunShot_handgun.play();
                    gunShot_handgun.setVolume(vf, 0.1f);
                }

                bullets.add(new Bullet(player.getX() + 5, player.getY() + 10,20));
                for (Bullet b : bullets) {
                    if (b.shot == false) {
                        b.shoot(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                    }
                }

            }
            else if (guns == 2) //shotgun
            {
                if (bullets.size() > 6) { //fire rate
                    return;
                }

                ////plays sound effect (and sets volume lower)
                if (bullets.size() < 6) {
                    long vf = gunShot_shotgun.play();
                    gunShot_shotgun.setVolume(vf, 0.1f);
                }


                int c = -20;
                bullets.add(new Bullet(player.getX()+ 5, player.getY()+ 10,25));
                bullets.add(new Bullet(player.getX()+ 5, player.getY()+ 10,25));
                bullets.add(new Bullet(player.getX()+ 5, player.getY()+ 10,25));
                bullets.add(new Bullet(player.getX()+ 5, player.getY()+ 10,25));

                //float distance = (float)Math.sqrt(Math.pow((Gdx.input.getX() - player.getX()), 2) + Math.pow(((Gdx.graphics.getHeight() - Gdx.input.getY()) - player.getY()), 2));

                for (int i = 0; i < bullets.size(); i++) {
                    if (bullets.get(i).shot == false) {
                        bullets.get(i).shoot(Gdx.input.getX()+ c, Gdx.graphics.getHeight() - Gdx.input.getY() + c);
                        c += 20;
                    }
                }
            }
            else if(guns == 3) //rpg
            {
                if (bullets.size() > 0) { //fire rate
                    return;
                }

                //plays sound effect (and sets volume lower)
                long vf = gunShot_rpg.play();
                gunShot_rpg.setVolume(vf, 0.1f);

                boom = 1; //NB for explosion
                Bullet blt = new Bullet(player.getX() + 5, player.getY() + 10,100);
                bullets.add(blt); //5 and 10
                for (Bullet b : bullets) {
                    if (b.shot == false) {
                        b.shoot(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                    }

                }
            }


        }
    }

    //shooting fourth gun
    public void Minigun()
    {
        if (Gdx.input.isTouched()) {
            if (guns == 4) {

                if (bullets.size() > 50) { //fire rate
                    return;
                }

                ////plays sound effect (and sets volume lower)
                if (bullets.size() < 50) {
                    long vf = gunShot_handgun.play();
                    gunShot_handgun.setVolume(vf, 0.05f);
                }

                bullets.add(new Bullet(player.getX() + 5, player.getY() + 10,5));
                for (Bullet b : bullets)
                {
                    if (b.shot == false) {
                        b.shoot(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                    }
                }

            }
        }
    }

    //deletes bullets after certain amount of time
    public void UpdateBulletAndExplosion(float delta)
    {
        for(int i = 0; i< bullets.size(); i++)
        {
            bullets.get(i).update(delta);
            if(bullets.get(i).Remove())
            {

                bulletHitX = bullets.get(i).getX();
                bulletHitY = bullets.get(i).getY();

                //explosion of rocket launcher
                if(guns==3) {
                    if(boom==1) {
                        Bullet exp = new Bullet(bulletHitX, bulletHitY,20);
                        Bullet exp1 = new Bullet(bulletHitX, bulletHitY,20);
                        Bullet exp2 = new Bullet(bulletHitX, bulletHitY,20);
                        Bullet exp3 = new Bullet(bulletHitX, bulletHitY,20);
                        Bullet exp4 = new Bullet(bulletHitX, bulletHitY,20);
                        Bullet exp5 = new Bullet(bulletHitX, bulletHitY,20);
                        Bullet exp6 = new Bullet(bulletHitX, bulletHitY,20);
                        Bullet exp7 = new Bullet(bulletHitX, bulletHitY,20);
                        Bullet exp8 = new Bullet(bulletHitX, bulletHitY,20);
                        Bullet exp9 = new Bullet(bulletHitX, bulletHitY,20);
                        Bullet exp10 = new Bullet(bulletHitX, bulletHitY,20);
                        Bullet exp11 = new Bullet(bulletHitX, bulletHitY,20);

                        bullets.add(exp);
                        bullets.add(exp1);
                        bullets.add(exp2);
                        bullets.add(exp3);
                        bullets.add(exp4);
                        bullets.add(exp5);
                        bullets.add(exp6);
                        bullets.add(exp7);
                        bullets.add(exp8);
                        bullets.add(exp9);
                        bullets.add(exp10);
                        bullets.add(exp11);

                        exp.shoot(bulletHitX, bulletHitY+500);
                        exp1.shoot(bulletHitX+500, bulletHitY+500);
                        exp2.shoot(bulletHitX+500, bulletHitY);
                        exp3.shoot(bulletHitX+500, bulletHitY-500);
                        exp4.shoot(bulletHitX, bulletHitY-500);
                        exp5.shoot(bulletHitX-500, bulletHitY-500);
                        exp6.shoot(bulletHitX-500, bulletHitY);
                        exp7.shoot(bulletHitX-500, bulletHitY+500);
                        exp8.shoot(bulletHitX+500, bulletHitY-250);
                        exp9.shoot(bulletHitX-500, bulletHitY-250);
                        exp10.shoot(bulletHitX-500, bulletHitY+250);
                        exp11.shoot(bulletHitX+500, bulletHitY+250);


                        long vf = explosion.play();
                        explosion.setVolume(vf, 0.1f);

                        boom++;
                    }
                }

                bullets.remove(i);
                i--;
            }
        }
    }

	//for focusing camera on character
	public void updateCamera(){
		camera.position.x = objects.get(0).getX();
		camera.update();
	}
}