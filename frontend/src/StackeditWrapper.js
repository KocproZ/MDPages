import React, {Component} from 'react';
import Stackedit from './Stackedit';

const stackedit = new Stackedit();

class StackeditWrapper extends Component {
    constructor(props) {
        super(props);

        this.textarea = React.createRef();
    }

    render() {
        stackedit.openFile({
            content: {
                text: "" // and the Markdown content.
            }
        });
        stackedit.on('fileChange', (file) => {
            this.textarea.current.value = file.content.text;
        });
        return (
            <div>
                <textarea ref={this.textarea}/>
            </div>
        )
    }
}

export default StackeditWrapper;
