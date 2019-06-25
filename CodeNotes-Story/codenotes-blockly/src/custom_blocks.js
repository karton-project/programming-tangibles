Blockly.defineBlocksWithJsonArray([
    // Block for colour picker.
    {
        "type": "next_story",
        "message0": "set next frame %1",
        "args0": [
          {
            "type": "field_number",
            "name": "NEXT_ID",
            "value": 0,
            "min": 0,
            "precision": 1
          }
        ],
        "previousStatement": null,
        "nextStatement": null,
        "colour": 230,
        "tooltip": "",
        "helpUrl": ""
      },
      {
        "type": "set_duration",
        "message0": "set duration %1",
        "args0": [
          {
            "type": "field_number",
            "name": "duration",
            "value": 0,
            "min": 0,
            "precision": 1
          }
        ],
        "previousStatement": null,
        "nextStatement": null,
        "colour": 230,
        "tooltip": "",
        "helpUrl": ""
      },
      {
        "type": "get_duration",
        "message0": "get duration from ID %1",
        "args0": [
          {
            "type": "field_number",
            "name": "duration_from_id",
            "value": 0,
            "min": 0,
            "precision": 1
          }
        ],
        "inputsInline": true,
        "previousStatement": null,
        "nextStatement": null,
        "colour": 230,
        "tooltip": "",
        "helpUrl": ""
      },
      {
        "type": "flash_mode",
        "message0": "flash %1",
        "args0": [
          {
            "type": "field_dropdown",
            "name": "mode",
            "options": [
              [
                "open",
                "open"
              ],
              [
                "close",
                "close"
              ]
            ]
          }
        ],
        "previousStatement": null,
        "nextStatement": null,
        "colour": 230,
        "tooltip": "",
        "helpUrl": ""
      },
      {
        "type": "camera_object_recognition",
        "message0": "camera detects %1",
        "args0": [
          {
            "type": "field_dropdown",
            "name": "recog_object_list",
            "options": [
              [
                "astronout",
                "astronout"
              ],
              [
                "snoopy",
                "snoopy"
              ],
              [
                "lizard",
                "lizard"
              ]
            ]
          }
        ],
        "output": "Boolean",
        "colour": 230,
        "tooltip": "",
        "helpUrl": ""
      },
      {
        "type": "camera_text_recognition",
        "message0": "camera recognizes %1",
        "args0": [
          {
            "type": "field_input",
            "name": "camera_text",
            "text": "text"
          }
        ],
        "output": "Boolean",
        "colour": 230,
        "tooltip": "",
        "helpUrl": ""
      },
      {
        "type": "previous_story",
        "message0": "set previous frame %1",
        "args0": [
          {
            "type": "field_number",
            "name": "PREV_ID",
            "value": 0,
            "min": 0,
            "precision": 1
          }
        ],
        "previousStatement": null,
        "nextStatement": null,
        "colour": 230,
        "tooltip": "",
        "helpUrl": ""
      },
      {
        "type": "ask_user",
        "message0": "ask user %1",
        "args0": [
          {
            "type": "field_input",
            "name": "question",
            "text": "question"
          }
        ],
        "output": "String",
        "colour": 230,
        "tooltip": "",
        "helpUrl": ""
      },
      {
        "type": "ask_question",
        "message0": "ask reader %1 save as %2",
        "args0": [
          {
            "type": "field_input",
            "name": "question",
            "text": "question"
          },
          {
            "type": "field_variable",
            "name": "answer",
            "variable": "answer"
          }
        ],
        "previousStatement": null,
        "nextStatement": null,
        "colour": 230,
        "tooltip": "",
        "helpUrl": ""
      },
      {
        "type": "add_frame",
        "message0": "add frame %1",
        "args0": [
          {
            "type": "input_statement",
            "name": "NAME"
          }
        ],
        "previousStatement": null,
        "nextStatement": null,
        "colour": 230,
        "tooltip": "",
        "helpUrl": ""
      },
      {
        "type": "set_title",
        "message0": "set title %1",
        "args0": [
          {
            "type": "field_input",
            "name": "title",
            "text": "New Title"
          }
        ],
        "previousStatement": null,
        "nextStatement": null,
        "colour": 230,
        "tooltip": "",
        "helpUrl": ""
      },
      {
        "type": "play_sound",
        "message0": "Play %1",
        "args0": [
          {
            "type": "field_dropdown",
            "name": "VALUE",
            "options": [
              ["C4", "../sounds/c4.m4a"],
              ["D4", "../sounds/d4.m4a"],
              ["E4", "../sounds/e4.m4a"],
              ["F4", "../sounds/f4.m4a"],
              ["G4", "../sounds/g4.m4a"],
              ["A5", "../sounds/a5.m4a"],
              ["B5", "../sounds/b5.m4a"],
              ["C5", "../sounds/c5.m4a"]
            ]
          }
        ],
        "previousStatement": null,
        "nextStatement": null,
        "colour": 355,
        "tooltip": "",
        "helpUrl": ""
      }
  ]);

  Blockly.JavaScript['play_sound'] = function(block) {
    var value = '\'' + block.getFieldValue('VALUE') + '\'';
    return 'MusicMaker.queueSound(' + value + ');\n';
  };

  Blockly.JavaScript['set_title'] = function(block) {
    var text_title = block.getFieldValue('title');
    var code = '<h3>' + text_title + '</h3>';
    return code;
  };  

  Blockly.JavaScript['add_frame'] = function(block) {
    var statements_name = Blockly.JavaScript.statementToCode(block, 'NAME');
    // TODO: Assemble JavaScript into code variable.
    var code = "Reveal.add('');\n";
    return code+statements_name;
  };
  
  Blockly.JavaScript['flash_mode'] = function (block) {
    var dropdown_mode = block.getFieldValue('mode');
    // TODO: Assemble JavaScript into code variable.
    var code = '(flash [' + dropdown_mode + '])';
    return code;
  };
  
  Blockly.JavaScript['camera_text_recognition'] = function (block) {
    var text_camera_text = block.getFieldValue('camera_text');
    // TODO: Assemble JavaScript into code variable.
    var code = '(is_recognize [' + text_camera_text + '])';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.JavaScript.ORDER_NONE];
  };
  
  Blockly.JavaScript['camera_object_recognition'] = function (block) {
    var dropdown_recog_object_list = block.getFieldValue('recog_object_list');
    // TODO: Assemble JavaScript into code variable.
    var code = '(is_detect [' + dropdown_recog_object_list + '])';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.JavaScript.ORDER_NONE];
  };
  
  Blockly.JavaScript['previous_story'] = function (block) {
    var number_prev_id = block.getFieldValue('PREV_ID');
    // TODO: Assemble JavaScript into code variable.
    var code = '(set_previous_story [' + number_prev_id + '])';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.JavaScript.ORDER_NONE];
  };
  
  Blockly.JavaScript['next_story'] = function (block) {
    var number_next_id = block.getFieldValue('NEXT_ID');
    // TODO: Assemble JavaScript into code variable.
    var code = '(set_next_story [' + number_next_id + '])';
    // TODO: Change ORDER_NONE to the correct strength.
    return code;
  };
  
  Blockly.JavaScript['ask_user'] = function(block) {
    var text_question = block.getFieldValue('question');
    // TODO: Assemble JavaScript into code variable.
    var code = 'var text;'+
    'var user_input = prompt("'+text_question+'", "Your answer here");'+
    'if (user_input == null || person == "") {'+
      'text = "User cancelled the prompt.";'+
    '} else {'+
      'text = user_input;'+
    '}';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.JavaScript.ORDER_NONE];
  };

  Blockly.JavaScript['ask_question'] = function(block) {
    var text_question = block.getFieldValue('question');
    var variable_answer = Blockly.JavaScript.variableDB_.getName(block.getFieldValue('answer'), Blockly.Variables.NAME_TYPE);
    // TODO: Assemble JavaScript into code variable.
    var code = 'var text;\n'+
    'var user_input = prompt("'+text_question+'", "Your answer here");\n'+
    'if (user_input == null) {\n'+
      'text = "User cancelled the prompt.";\n'+
    '} else {\n'+
      'text = user_input;\n'+
    '}\n'+
    'StoryCreator.setView("../img/frame3.png");\n'+
    'console.log(StoryCreator.questions_);\n';
    return code;
  };
  
  Blockly.JavaScript['set_duration'] = function (block) {
    var number_duration = block.getFieldValue('duration');
    // TODO: Assemble JavaScript into code variable.
    var code = '(set_duration [' + number_duration + '])';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.JavaScript.ORDER_NONE];
  };
  
  Blockly.JavaScript['get_duration'] = function(block) {
    var number_duration_from_id = block.getFieldValue('duration_from_id');
    // TODO: Assemble JavaScript into code variable.
    var code = '(get_duration [' + number_duration_from_id + '])';
    return code;
  };
  