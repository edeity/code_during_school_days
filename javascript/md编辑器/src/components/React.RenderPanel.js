/**
 * Created by edeity on 16/1/4.
 */
import React, { Component } from 'react';
import marked from 'marked';
//use pygmentize install of hightlight becauseof performance
import hl from 'highlight.js';

class RenderPanel extends Component {
    constructor() {
        super();
        this.state = {
            shouldUpdate : true,
            shouldDelayUpdate: 60,
            isCalculting: false
        }
        //marked.setOptions({
        //    highlight: function (code) {
        //        return hl.highlightAuto(code).value;
        //    }
        //});
    }
    componentDidUpdate() {
        if(this.props.isSyncScroll) {
            //更新滚动高度, 当syncScroll为true时才同步,当syncScroll不同步时,可自由滚动
            let render = this.refs.render;
            render.scrollTop = this.props.scrollRatio * (render.scrollHeight - render.clientHeight);
        }
    }
    // 采用延迟渲染: 在实时渲染过程中,假如超过3000字,则可能会很卡.
    componentWillReceiveProps(nextProps) {
        const oldProps = this.props;
        if(nextProps.scrollRatio != oldProps.scrollRatio) {
            // react仅更新scroll, 所以滚动可每次滚动紧接着更新
            this.setState({shouldUpdate: true});
        }else if(nextProps.content != oldProps.content ){
            // 延迟更新,每次content的改变不一定就要更新内容,而是没30次应该更新的时候,进行真正的更新
            const shouldDelayUpdate = this.state.shouldDelayUpdate;
            shouldDelayUpdate > 0
                ? this.setState({shouldDelayUpdate: shouldDelayUpdate - 1})
                : this.setState({shouldDelayUpdate: 30, shouldUpdate: true});

            // 假如长时间没有改变,则应该主动刷新
            if(!this.state.isCalculting) {
                this.setState({isCalculting: true});
                window.setTimeout(()=>{
                    this.setState({isCalculting: false, shouldDelayUpdate: 30, shouldUpdate: true});
                }, 1000)
            }
        } else {
            this.setState({shouldUpdate: false})
        }
    }
    shouldComponentUpdate() {
        return this.state.shouldUpdate;
    }
    markup = () => {
        //测试渲染速度
        //return {__html:  ""};
        return {__html: marked( this.props.content)};
    }
    handleScroll = () => {
        // 当单独滚动Render时,不同步滚动
        this.props.handleSyncScroll(false);
    }
    render() {
        return (
            <div id="render-panel" ref="render" className="markdown-body"
                 onScroll={this.handleScroll}
                 dangerouslySetInnerHTML={this.markup()}>
            </div>
        )
    }
}

export default RenderPanel;