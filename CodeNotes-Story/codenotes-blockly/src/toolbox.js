'use strict';

/** Create a namespace for the application. */
var CodeNotes = CodeNotes || {};

CodeNotes.TOOLBOX_XML =

'<xml id="toolbox">' +
'<category name="Logic" colour="#5C81A6">'+
'<block type="controls_if"></block>'+
'<block type="logic_compare">'+
'  <field name="OP">EQ</field>'+
'</block>'+
'<block type="logic_operation">'+
'  <field name="OP">AND</field>'+
'</block>'+
'<block type="logic_negate"></block>'+
'<block type="logic_boolean">'+
'  <field name="BOOL">TRUE</field>'+
'</block>'+
'<block type="logic_null"></block>'+
'<block type="camera_text_recognition"></block>'+
'<block type="camera_object_recognition"></block>'+
'<block type="logic_ternary"></block>'+
'</category>'+
'<category name="Math" colour="#5C68A6">'+
'<block type="math_round">'+
'  <field name="OP">ROUND</field>'+
'  <value name="NUM">'+
'    <shadow type="math_number">'+
'      <field name="NUM">3.1</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="math_number">'+
'  <field name="NUM">0</field>'+
'</block>'+
'<block type="math_single">'+
'  <field name="OP">ROOT</field>'+
'  <value name="NUM">'+
'    <shadow type="math_number">'+
'      <field name="NUM">9</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="math_trig">'+
'  <field name="OP">SIN</field>'+
'  <value name="NUM">'+
'    <shadow type="math_number">'+
'      <field name="NUM">45</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="math_constant">'+
'  <field name="CONSTANT">PI</field>'+
'</block>'+
'<block type="math_number_property">'+
'  <mutation divisor_input="false"></mutation>'+
'  <field name="PROPERTY">EVEN</field>'+
'  <value name="NUMBER_TO_CHECK">'+
'    <shadow type="math_number">'+
'      <field name="NUM">0</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="math_arithmetic">'+
'  <field name="OP">ADD</field>'+
'  <value name="A">'+
'    <shadow type="math_number">'+
'      <field name="NUM">1</field>'+
'    </shadow>'+
'  </value>'+
'  <value name="B">'+
'    <shadow type="math_number">'+
'      <field name="NUM">1</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="math_constrain">'+
'  <value name="VALUE">'+
'    <shadow type="math_number">'+
'      <field name="NUM">50</field>'+
'    </shadow>'+
'  </value>'+
'  <value name="LOW">'+
'    <shadow type="math_number">'+
'      <field name="NUM">1</field>'+
'    </shadow>'+
'  </value>'+
'  <value name="HIGH">'+
'    <shadow type="math_number">'+
'      <field name="NUM">100</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="math_random_int">'+
'  <value name="FROM">'+
'    <shadow type="math_number">'+
'      <field name="NUM">1</field>'+
'    </shadow>'+
'  </value>'+
'  <value name="TO">'+
'    <shadow type="math_number">'+
'      <field name="NUM">100</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="math_random_float"></block>'+
'</category>'+
'<category name="Loops" colour="#5CA65C">'+
'<block type="controls_repeat_ext">'+
'  <value name="TIMES">'+
'    <shadow type="math_number">'+
'      <field name="NUM">10</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="controls_whileUntil">'+
'  <field name="MODE">WHILE</field>'+
'</block>'+
'<block type="controls_for">'+
'  <field name="VAR" id="wIt;{k)-eiHJWL2S}cy+" variabletype="">i</field>'+
'  <value name="FROM">'+
'    <shadow type="math_number">'+
'      <field name="NUM">1</field>'+
'    </shadow>'+
'  </value>'+
'  <value name="TO">'+
'    <shadow type="math_number">'+
'      <field name="NUM">10</field>'+
'    </shadow>'+
'  </value>'+
'  <value name="BY">'+
'    <shadow type="math_number">'+
'      <field name="NUM">1</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="controls_forEach">'+
'  <field name="VAR" id="KdI^nB@0T4,0$A}%u%rT" variabletype="">j</field>'+
'</block>'+
'<block type="controls_flow_statements" disabled="true">'+
'  <field name="FLOW">BREAK</field>'+
'</block>'+
'</category>'+
'<category name="Text" colour="160">'+
'<block type="text"></block>'+
'<block type="text_append">'+
'  <value name="TEXT">'+
'    <shadow type="text"></shadow>'+
'  </value>'+
'</block>'+
'<block type="text_length">'+
'  <value name="VALUE">'+
'    <shadow type="text">'+
'      <field name="TEXT">abc</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="text_isEmpty">'+
'  <value name="VALUE">'+
'    <shadow type="text"></shadow>'+
'  </value>'+
'</block>'+
'<block type="text_indexOf">'+
'  <value name="VALUE">'+
'    <block type="variables_get">'+
'      <field name="VAR">text</field>'+
'    </block>'+
'  </value>'+
'  <value name="FIND">'+
'    <shadow type="text">'+
'      <field name="TEXT">abc</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="text_charAt">'+
'  <value name="VALUE">'+
'    <block type="variables_get">'+
'      <field name="VAR">text</field>'+
'    </block>'+
'  </value>'+
'</block>'+
'<block type="text_getSubstring">'+
'  <value name="STRING">'+
'    <block type="variables_get">'+
'      <field name="VAR">text</field>'+
'    </block>'+
'  </value>'+
'</block>'+
'<block type="text_changeCase">'+
'  <value name="TEXT">'+
'    <shadow type="text">'+
'      <field name="TEXT">abc</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="text_trim">'+
'  <value name="TEXT">'+
'    <shadow type="text">'+
'      <field name="TEXT">abc</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="text_print">'+
'  <value name="TEXT">'+
'    <shadow type="text">'+
'      <field name="TEXT">abc</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="text_prompt_ext">'+
'  <value name="TEXT">'+
'    <shadow type="text">'+
'      <field name="TEXT">abc</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'</category>'+
'<category name="Lists" colour="260">'+
'<block type="lists_create_empty"></block>'+
'<block type="lists_repeat">'+
'  <value name="NUM">'+
'    <shadow type="math_number">'+
'      <field name="NUM">5</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="lists_length"></block>'+
'<block type="lists_isEmpty"></block>'+
'<block type="lists_indexOf">'+
'  <value name="VALUE">'+
'    <block type="variables_get">'+
'      <field name="VAR">list</field>'+
'    </block>'+
'  </value>'+
'</block>'+
'<block type="lists_getIndex">'+
'  <value name="VALUE">'+
'    <block type="variables_get">'+
'      <field name="VAR">list</field>'+
'    </block>'+
'  </value>'+
'</block>'+
'<block type="lists_setIndex">'+
'  <value name="LIST">'+
'    <block type="variables_get">'+
'      <field name="VAR">list</field>'+
'    </block>'+
'  </value>'+
'</block>'+
'<block type="lists_getSublist">'+
'  <value name="LIST">'+
'    <block type="variables_get">'+
'      <field name="VAR">list</field>'+
'    </block>'+
'  </value>'+
'</block>'+
'<block type="lists_sort"></block>'+
'</category>'+
'<category name="Colour" colour="20">'+
'<block type="colour_picker"></block>'+
'<block type="colour_random"></block>'+
'<block type="colour_rgb">'+
'  <value name="RED">'+
'    <shadow type="math_number">'+
'      <field name="NUM">100</field>'+
'    </shadow>'+
'  </value>'+
'  <value name="GREEN">'+
'    <shadow type="math_number">'+
'      <field name="NUM">50</field>'+
'    </shadow>'+
'  </value>'+
'  <value name="BLUE">'+
'    <shadow type="math_number">'+
'      <field name="NUM">0</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'<block type="colour_blend">'+
'  <value name="COLOUR1">'+
'    <shadow type="colour_picker">'+
'      <field name="COLOUR">#ff0000</field>'+
'    </shadow>'+
'  </value>'+
'  <value name="COLOUR2">'+
'    <shadow type="colour_picker">'+
'      <field name="COLOUR">#3333ff</field>'+
'    </shadow>'+
'  </value>'+
'  <value name="RATIO">'+
'    <shadow type="math_number">'+
'      <field name="NUM">0.5</field>'+
'    </shadow>'+
'  </value>'+
'</block>'+
'</category>'+
'<category name="Sounds" colour="355">' +
    '<block type="play_sound"></block>' +
'</category>' +
'<category name="Story" colour="#a5805b">'+
    '<block type="previous_story"></block>'+
    '<block type="next_story"></block>'+
    '<block type="set_title"></block>'+
    '<block type="set_duration"></block>'+
    '<block type="get_duration"></block>'+
    '<block type="ask_user"></block>'+
    '<block type="ask_question"></block>'+
    '<block type="add_frame"></block>'+
'</category>'+
'<sep></sep>' +
'<category name="Variables" colour="%{BKY_VARIABLES_HUE}" custom="VARIABLE"></category>' +
'<category name="Functions" colour="%{BKY_PROCEDURES_HUE}" custom="PROCEDURE"></category>' +
'</xml>'