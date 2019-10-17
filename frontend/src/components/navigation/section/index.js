import React, {Component} from 'react';
import './style.scss';

class Section extends Component {
    constructor(props) {
        super(props);
        this.state = {
            title: this.props.title,
            color: this.props.color
        };
    }

    render() {
        const Title = () => {
            if (this.state.title === undefined)
                return "";
            return <span className={"title" + (this.state.color ? " " + this.state.color : "")}>{
                this.state.title
            }</span>
        };
        return (
            <div className={"section"}>
                <Title/>
                {this.props.children}
            </div>
        );
    }
}

export default Section;

