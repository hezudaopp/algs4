
public class Ball {
	private final double radius = 0.01;
	private double velocityX, velocityY;
	private double positionX, positionY;
	
	public Ball() {
		this.velocityX = 0.05 - StdRandom.uniform()*0.1;
		this.velocityY = 0.05 - StdRandom.uniform()*0.1;
		this.positionX = StdRandom.uniform();
		this.positionY = StdRandom.uniform();
	}
	
	public void move(double time) {
		double nextTimePositionX = this.positionX + this.velocityX * time;
		double nextTimePositionY = this.positionY + this.velocityY * time;
		if (nextTimePositionX < this.radius || nextTimePositionX > 1.0 - this.radius) this.velocityX = -this.velocityX;
		if (nextTimePositionY < this.radius || nextTimePositionY > 1.0 - this.radius) this.velocityY = -this.velocityY;
		this.positionX = nextTimePositionX;
		this.positionY = nextTimePositionY;
	}
	
	public void draw() {
		StdDraw.filledCircle(this.positionX, this.positionY, this.radius);
	}
	
}
