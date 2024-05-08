import {VerticalLayout} from "@hilla/react-components/VerticalLayout";
import {TextField} from "@hilla/react-components/TextField";
import {Button} from "@hilla/react-components/Button";
import {HelloEndpoint} from "Frontend/generated/endpoints";
import {useState} from "react";

export default function CreateListingView() {
    const [name, setName] = useState("");
    const [notifications, setNotifications] = useState([] as string[]);

    return (
        <>
            <VerticalLayout className={'centered-content'}>
                <h3>Hilla View</h3>
                <TextField
                    label="Your name"
                    onValueChanged={(e) => {
                        setName(e.detail.value);
                    }}
                />
                <Button
                    onClick={async () => {
                        alert("Tada")
                    }}
                > Say hello </Button>
                {notifications.map((notification, index) => (
                    <p key={index}>{notification}</p>
                ))}
            </VerticalLayout>
        </>
    );
}