import React, {Component} from 'react';
import './style.scss';
import {connect} from "react-redux";
import {getNavigationVisibility} from "../../../redux/selectors";
import {toggleNavigation} from "../../../redux/actions";

class MobileButton extends Component {

    render() {
        return (
            <div className={"mobile-button"}>
                <input type="checkbox" onChange={() => this.props.dispatch(toggleNavigation())} checked={this.props.visibility}/>
                <span/>
                <span/>
                <span/>
            </div>
        );
    }
}

const mapStateToProps = state => {
    const visibility = getNavigationVisibility(state);
    return {visibility}
};

export default connect(mapStateToProps)(MobileButton);
