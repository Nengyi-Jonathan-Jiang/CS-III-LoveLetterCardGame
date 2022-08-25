let c = new Canvas(0, 0, document.body);
window.onresize = _=>c.resizeToWindow();
let game = new Game(c.canvas);

class Card{
    constructor(name, value, ...description){
        this.name = name;
        this.value = value;
        this.description = description;
    }

    draw(x, y, scale){
        c.setDrawColor("#000");
        c.drawRect(x, y, x + scale, y + 2 * scale);
        c.fillText(this.name + ": " + this.value, x + scale * .5, y + scale * 1, scale * 0.2);
    }
}

class TheGame{
    /** @param {Player[]} players */
    constructor(players){
        this.players = players;
        this._currPlayer = 0;
        this.deck = [
            new Card("Spy", 0),
            new Card("Guard", 1),
            new Card("Spy", 0),
            new Card("Handmaid", 4),
            new Card("Guard", 1),
            new Card("Guard", 1),
            new Card("Prince", 5),
            new Card("Spy", 0),
            new Card("Guard", 1),
            new Card("Spy", 0),
            new Card("Handmaid", 4),
            new Card("Guard", 1),
            new Card("Guard", 1),
            new Card("Prince", 5),
            new Card("Spy", 0),
            new Card("Guard", 1),
            new Card("Spy", 0),
            new Card("Handmaid", 4),
            new Card("Guard", 1),
            new Card("Guard", 1),
            new Card("Prince", 5),
            new Card("Spy", 0),
            new Card("Guard", 1),
            new Card("Spy", 0),
            new Card("Handmaid", 4),
            new Card("Guard", 1),
            new Card("Guard", 1),
            new Card("Prince", 5),
        ];
    }

    get currPlayer(){
        return this.players[this._currPlayer];
    }

    nextPlayer(){
        this._currPlayer++;
        this._currPlayer %= this.players.length;
    }
}

class Player{
    constructor(name){
        this.name = name;
        /** @type {Card[]} */
        this.discard = [];
        /** @type {Card} */
        this.active = null;
        /** @type {Card[]} */
        this.hand = [];
    }

    drawHand(){
        for(let i = 0; i < this.hand.length; i++){
            this.hand[i].draw((i + 0.5) * c.width / this.hand.length - 100, c.height - 450, 200);
        }
    }

    
}

let theGame = new TheGame([new Player("John"), new Player("Alex"), new Player("Robert")]);

game.addScene("StartGame", new Scene(function run(data){
    c.clear();
    c.fillText("Start", c.width / 2, c.height / 2, 50);
}, {
    events: {
        click: _=>{
            theGame.players.forEach(player=>player.hand.push(theGame.deck.pop()));
            game.gotoScene("StartTurn");
        }
    }
}, function onstart(){

}));

game.addScene("StartTurn", new Scene(function run(data){
    c.clear();
    c.fillText(theGame.currPlayer.name +  "'s turn", c.width / 2, c.height / 2, 50);
}, {
    events: {
        click: _=>{
            game.gotoScene("DrawCards");
        }
    }
}, function onstart(){
    theGame.nextPlayer();
}));

game.addScene("DrawCards", new Scene(function run(data){
    if(!run.animationTime || run.animationTime >= 120) run.animationTime = 0;
    run.animationTime++;

    let player = theGame.currPlayer;

    if(run.animationTime >= 120){
        player.hand.push(theGame.deck.pop());
        game.gotoScene("AwaitPlayCard");
    }

    let drawnCard = theGame.deck[theGame.deck.length - 1];
    let hand = player.hand;

    c.clear();
    c.fillText("Running Animation", c.width / 2, c.height / 2, 50);

    player.drawHand();
}, {

}, function onstart(){
    let player = theGame.currPlayer;
}));

game.addScene("AwaitPlayCard", new Scene(function run(data){

    c.clear();
    c.fillText("Running Animation", c.width / 2, c.height / 2, 50);

    let player = theGame.currPlayer;
    let hand = player.hand;

    
    player.drawHand();
    
}, {
    events: {
        click: e=>{
            if(e.clientY > c.height - 500){
                let player = theGame.currPlayer;

                let picked = ~~(player.hand.length * e.clientX / c.width);
                console.log(player.name + " used " + player.hand[picked].name);
                
                game.gotoScene("StartTurn");
            }
        }
    }
}, function onstart(){

}));

game.addScene("AwaitDiscardCards", new Scene(function run(data){

}, {

}, function onstart(){
    
}));

game.run("StartGame");