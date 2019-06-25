// Main Functions to Handle Running Story
(function () {

  function handlePlay(event) {
    Blockly.JavaScript.addReservedWords('code');
    var code = Blockly.JavaScript.workspaceToCode(Blockly.getMainWorkspace());
    console.log(code);
    code += 'MusicMaker.play();';
    // Eval can be dangerous. For more controlled execution, check
    // https://github.com/NeilFraser/JS-Interpreter.
    try {
      console.log(code);
      eval(code);
    } catch (error) {
      console.log(error);
    }
  }

  function handleStoryCreation(event) {
    Blockly.JavaScript.addReservedWords('code');
    var code = Blockly.JavaScript.workspaceToCode(Blockly.getMainWorkspace());
    console.log(code);
    code += 'StoryCreator.create()';
    try {
      console.log(code);
      eval(code);
    } catch (error) {
      console.log(error);
    }
  }

  document.querySelector('#play').addEventListener('click', handlePlay);
  document.querySelector('#createStory').addEventListener('click', handleStoryCreation);
})();
