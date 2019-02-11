// code in cmd (module.exports) can be imported in es6
import fs from './tools/file'

// it should import React event though React is not used obvisouly
import React, { Component } from 'react';
import StatePanel from './React.StatePanel';
import InputPanel from './React.InputPanel';
import RenderPanel from './React.RenderPanel';
import MessagePanel from './React.MessagePanel';

export default class Mou extends Component {
	constructor() {
		super();
		this.state = {
            content: '',
            fileName: 'undefined',

            msg: '',

            isSyncScroll: true,
			scrollRatio: 0,
		}
	}
    //保存
    handleKeyDown = (event) => {
        //Ctrl + s: 保存
        if(event.ctrlKey && event.keyCode === 83) {
            // 保存文件
            fs.save(this.state.fileName+'.md', this.state.content);
            return event.preventDefault() && event.stopProgation();
        }
    };

    // 这种格式就不用再下面调用时handleContentChange.bind(this)了
	handleContentChange = (content) => {
		this.setState({content: content});
	};
    handleFileNameChange = (fileName) => {
        this.setState({fileName: fileName});
    };
    handleShowMessage = (msg) => {
        this.setState({msg: msg});
    };
    handleSyncScroll = (isSync) => {
        this.setState({isSyncScroll: isSync});
    }
	handleScroll = (scrollRatio) => {
		this.setState({scrollRatio: scrollRatio});
	};
	render() {
		return (
			<div id="mou-app"
                 onKeyDown={this.handleKeyDown}>

				<StatePanel fileName={this.state.fileName}
                            handleFileNameChange={this.handleFileNameChange}/>

				<InputPanel content={this.state.content}
                            handleFileNameChange={this.handleFileNameChange}
                            handleContentChange={this.handleContentChange}
                            handleShowMessage={this.handleShowMessage}
                            handleSyncScroll={this.handleSyncScroll}
                            handleScroll={this.handleScroll}/>

				<RenderPanel content={this.state.content}
                             isSyncScroll={this.state.isSyncScroll}
                             scrollRatio={this.state.scrollRatio}
                             handleSyncScroll={this.handleSyncScroll}/>

                <MessagePanel msg={this.state.msg}
                              handleShowMessage={this.handleShowMessage}/>
			</div>
		)
	}
}