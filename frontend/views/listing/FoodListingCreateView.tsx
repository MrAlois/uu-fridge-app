import React from 'react';
import {FormLayout} from "@hilla/react-components/FormLayout";

const FoodListingCreateView: React.FC = () => {

    const handleSubmit = (values: any) => {
        console.log(values); // here are your form values
    };

    return (
        <FormLayout onSubmit={handleSubmit}>

        </FormLayout>
    );
};

export default FoodListingCreateView;