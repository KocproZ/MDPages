import React, {Component} from 'react';
import './style.scss';

class Login extends Component {
    render() {
        return (
            <div className={"loginPage container"}>
                <form className={"loginForm"}>
                    <div className={"row"}>
                        <div className={"row col-5"}>
                            <div className={"login col-12"}><input type={"text"} placeholder={"Login"}/></div>
                            <div className={"login col-12"}><input type={"password"} placeholder={"password"}/></div>
                        </div>
                        <div className={"vertical-line col-1"}/>
                        <div className={"row col-5"}>
                            <div className={"button col-12"}>GitHub</div>
                            <div className={"button col-12"}>Cos</div>
                            <div className={"button col-12"}>GitHub</div>
                        </div>
                    </div>
                </form>
            </div>
        );
    }
}

export default Login;
