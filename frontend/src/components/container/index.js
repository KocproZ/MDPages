import React, {Component} from 'react';
import './style.scss';
import Footer from "../footer";

class Container extends Component {
    render() {
        return (
            <div className={"app"}>
                <div>
                    <div className={"container"}>

                    </div>
                    <Footer/>
                </div>
            </div>
        );
    }
}

export default Container;