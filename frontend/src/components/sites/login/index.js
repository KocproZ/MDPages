import React, {Component} from 'react';
import './style.scss';
import {connect} from "react-redux";
import {renameTitle} from "../../../redux/actions";

class LoginPage extends Component {
    componentDidMount() {
        this.props.dispatch(renameTitle("MDPages | Login"));
    }

    render() {
        return (
            <React.Fragment>

            </React.Fragment>
        );
    }
}

export default connect()(LoginPage);
