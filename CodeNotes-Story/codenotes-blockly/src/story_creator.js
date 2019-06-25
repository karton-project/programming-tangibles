// Factory method
// Story holds the frames executes the program
const StoryCreator = {
    
    frames_: [],

    addNewFrame: function (frame) {
        this.frames_.push(frame);
    },

    setView: function (fileUrl) {
        document.getElementById("storyImageContainer").src = fileUrl;  
    },

    create: function () {
        let nextFrame = this.frames_.shift();
        if (nextFrame) {
            this.setView(nextFrame.fileUrl_);
            nextFrame.runCode();
        }
    },
};

// Frames are components of a story
// Holds fileUrl to load image, video and gifs
class Frame{

    constructor(name, fileUrl) {
        this.name_ = name;
        this.fileUrl_ = fileUrl;
        this.code_;
    }

    updateCode(updatedCode){
        this.code_ = updatedCode;
    }

    updateBackground(fileUrl){
        this.fileUrl_ = fileUrl;
    }

    runCode(){
        if(!isBlank(this.code_)){
            alert(this.code_, this.url_);
        }
    }
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

StoryCreator.player_.addEventListener(
    'ended', StoryCreator.create.bind(StoryCreator));
