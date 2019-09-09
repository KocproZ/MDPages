import React, {Component} from 'react';
import './style.scss';
import Search from "./search/";
import Logo from "../logo";
import MobileButton from "./mobile-button";
import {connect} from "react-redux";
import {getNavigationVisibility} from "../../redux/selectors";

class Navigation extends Component {

    render() {
        return (
            <div className={"navigation" + (this.props.visibility ? " show" : "")}>
                <Logo/>
                <Search/>
                <MobileButton/>
            </div>
        );
    }
}

const mapStateToProps = state => {
    const visibility = getNavigationVisibility(state);
    return {visibility}
};

export default connect(mapStateToProps)(Navigation);
