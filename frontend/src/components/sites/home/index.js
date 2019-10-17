import React, {Component} from 'react';
import './style.scss';
import {connect} from "react-redux";
import {renameTitle} from "../../../redux/actions";

class Home extends Component {

    componentDidMount() {
        this.props.dispatch(renameTitle("MDPages | Homepage"));
    }

    render() {
        return (
            <React.Fragment>
                <div>KUPA</div>
            </React.Fragment>
        );
    }
}

export default connect()(Home);
