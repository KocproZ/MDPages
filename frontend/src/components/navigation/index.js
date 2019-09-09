import React, {Component} from 'react';
import './style.scss';
import Search from "./search/";
import Logo from "../logo";
import MobileButton from "./mobile-button";
import {connect} from "react-redux";
import {getNavigationVisibility} from "../../redux/selectors";
import {toggleNavigation} from "../../redux/actions";

class Navigation extends Component {
    constructor(props) {
        super(props);
        this.state = {width: 0, height: 0};
        this.updateWindowDimensions = this.updateWindowDimensions.bind(this);
    }

    componentDidMount() {
        this.updateWindowDimensions();
        window.addEventListener('resize', this.updateWindowDimensions);
    }

    componentWillUnmount() {
        window.removeEventListener('resize', this.updateWindowDimensions);
    }

    updateWindowDimensions() {
        this.setState({width: window.innerWidth, height: window.innerHeight});
        if (this.state.width < 768 && this.props.visibility === true) {
            this.props.dispatch(toggleNavigation())
        }
        else if(this.state.width > 768 && this.props.visibility === false){
            this.props.dispatch(toggleNavigation())
        }
    }

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
