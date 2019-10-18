import React, {Component} from 'react';

class FormGroup extends Component {
    render() {
        if (this.props.label)
            return <div className={"row col-12 form-group"}>
                <label htmlFor={this.props.name} className={"col-12"}>{this.props.label}</label>
                <input id={this.props.name} type={this.props.type} name={this.props.name} className={"col-12"}
                       required={this.props.required} value={this.props.value} onChange={this.props.handleChange}/>
            </div>;
        return <div className={"row col-12 form-group"}>
            <input id={this.props.name} type={this.props.type} name={this.props.name} className={"col-12"}
                   required={this.props.required} value={this.props.value}/>
        </div>;
    }
}

export default FormGroup;
