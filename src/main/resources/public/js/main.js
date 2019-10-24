let config = {
    type: Phaser.AUTO,
    width: 800,
    height: 600,
    scene: {
        preload: preload,
        create: create,
        update: update
    }
};

let game = new Phaser.Game(config);

function preload ()
{
    this.load.image('background', 'images/brickWall.jpg');
}

function create ()
{
    this.add.image(400, 300, 'background');
}

function update ()
{
}