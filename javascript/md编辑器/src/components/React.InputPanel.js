/**
 * Created by edeity on 16/1/4.
 */
import React, { Component } from 'react';

//see : https://github.com/securingsincity/react-ace
import brace from 'brace';
import AceEditor from './React.Ace';
import 'brace/mode/markdown';
import 'brace/theme/github';

// enable to drop
import Dropzone from 'react-dropzone';


export default class InputPanel extends Component {
    constructor() {
        super();
    }
    // 处理输入框文本更改
    onChange = (newValue) => {
        //this.props.handleContentChange(this.refs.input.value);
        this.props.handleContentChange(newValue);
    };
    // 处理滚动同步
    onScroll = (scrollRatio) => {
        // 采用textarea时的同步系数
        //let target = event.nativeEvent.target;
        //let scrollRatio = target.scrollTop / (target.scrollHeight - target.clientHeight);

        // 当inputScroll滚动时,开启同步滚动
        this.props.handleSyncScroll(true);
        this.props.handleScroll(scrollRatio);
    };
    // 处理拖拽文件
    onDrop = (files) =>{
        // <Dropzone accept="text/markdown"></Dropzone> doesn't work
        const acceptType = "text/markdown";
        if(files[0].type === acceptType) {
            this.props.handleFileNameChange(files[0].name);
            const fr = new FileReader();
            fr.readAsText(files[0]);
            fr.onload = function(e) {
                this.props.handleContentChange(e.target.result)
            }.bind(this);
        }else {
            this.props.handleShowMessage('仅支持markdown格式');
        }
    };
    render() {
        return (
            <Dropzone className="draggable"
                      onDrop={this.onDrop}
                      disableClick={true}
                      multiple={false}
                      disablePreview={false}>
                <AceEditor
                    className="input-panel"
                    mode="markdown"
                    theme="github"
                    value={this.props.content}
                    onChange={this.onChange}
                    onScroll={this.onScroll}
                    editorProps={{$blockScrolling: true}}/>
            </Dropzone>
        )
    }
}