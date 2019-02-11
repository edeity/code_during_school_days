/**
 * Created by edeity on 1/7/16.
 */
import React, { Component } from 'react';

export default class MessagePanel extends Component {
    constructor() {
        super();
    }
    componentWillUpdate() {
        if(!this.props.msg) {
            setTimeout(() => {
                this.props.handleShowMessage('');
            }, 2000);
        }
    }
    render() {
        let className = this.props.msg ? 'show-msg' : 'hide-msg';
        return (
            <div id="msg-panel" className={className}>
                {this.props.msg}
            </div>
        )
    }
}