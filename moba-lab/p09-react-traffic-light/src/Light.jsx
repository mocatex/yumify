import PropTypes from "prop-types";
import "./Light.css";


export default function Light({color, active = false}) {
    return (
        <div
            className="light"
            style={{backgroundColor: color, opacity: active ? 1 : 0.2}} />
    );
}

Light.propTypes = {
    color: PropTypes.string.isRequired,
    active: PropTypes.bool
}

Light.defaultProps = {
    active: false
}