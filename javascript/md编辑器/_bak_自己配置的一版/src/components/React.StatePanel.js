/**
 * Created by edeity on 16/1/4.
 */
import React, { Component } from 'react';

export default class StatePanel extends Component {
    constructor() {
        super();
        this.state = {
            isFocus : false
        }
    }
    handleChange = () => {
        this.props.handleFileNameChange(this.refs.fileNameInput.value);
    }
    handleFocus = () => {
        this.setState({isFocus: !this.state.isFocus})
    }
    render() {
        return (
            <div id="state-panel">
                {
                    this.state.isFocus
                        ? <input value={this.props.fileName}
                                 ref="fileNameInput"
                                 onChange={this.handleChange}
                                 onBlur={this.handleFocus}/>
                        : <span onClick={this.handleFocus}>{this.props.fileName}</span>
                }
            </div>
        )
    }
}