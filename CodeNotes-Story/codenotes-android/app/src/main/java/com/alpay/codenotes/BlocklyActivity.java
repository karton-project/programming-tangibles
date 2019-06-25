/*
 *  Copyright 2016 Google Inc. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.alpay.codenotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alpay.codenotes.models.Frame;
import com.alpay.codenotes.utils.NavigationManager;
import com.alpay.codenotes.utils.interpreter.BlocklyCompiler;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.model.DefaultBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlocklyActivity extends AbstractBlocklyActivity {
    private static final String TAG = "BlocklyActivity";
    private static int frameID = -1;

    public static void setFrameID(int frameID) {
        BlocklyActivity.frameID = frameID;
    }

    static final List<String> BLOCK_DEFINITIONS = Arrays.asList(
            DefaultBlocks.COLOR_BLOCKS_PATH,
            DefaultBlocks.LOGIC_BLOCKS_PATH,
            DefaultBlocks.LOOP_BLOCKS_PATH,
            DefaultBlocks.MATH_BLOCKS_PATH,
            DefaultBlocks.TEXT_BLOCKS_PATH,
            DefaultBlocks.LIST_BLOCKS_PATH,
            DefaultBlocks.VARIABLE_BLOCKS_PATH,
            "blockly/definitions.json"
    );

    private final CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new CodeGenerationRequest.CodeGeneratorCallback() {
                @Override
                public void onFinishCodeGeneration(final String generatedCode) {
                    saveFrameCode(generatedCode);
                }
            };

    private void saveFrameCode(String generatedCode) {
        // TODO: create intents as the results of generated code
        Toast.makeText(this, generatedCode, Toast.LENGTH_LONG).show();
        generatedCode = BlocklyCompiler.compile(generatedCode);
        if (frameID > 0) {
            Frame.updateFrameCode(frameID, generatedCode);
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.blockly_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_camera_compiler:
                Intent intent = new Intent(this, CodeNotesCompilerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.action_run:
                if (getController().getWorkspace().hasBlocks()) {
                    onRunCode();
                } else {
                    Toast.makeText(this, R.string.no_block_error, Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            frameID = bundle.getInt(NavigationManager.BUNDLE_KEY);
        }
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreateActivityRootView() {
        super.onCreateActivityRootView();
    }

    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        return BLOCK_DEFINITIONS;
    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "blockly/toolbox.xml";
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        List<String> paths = new ArrayList<String>(1);
        paths.add("blockly/generators.js");
        return paths;
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        return mCodeGeneratorCallback;
    }
}
