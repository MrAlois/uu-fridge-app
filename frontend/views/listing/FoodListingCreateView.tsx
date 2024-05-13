import React, {useEffect, useState} from 'react';
import {FormLayout} from "@hilla/react-components/FormLayout";
import {TextField} from "@hilla/react-components/TextField";
import {Button} from "@hilla/react-components/Button";
import {HorizontalLayout} from "@hilla/react-components/HorizontalLayout";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout";
import {Upload} from "@hilla/react-components/Upload";
import {IntegerField} from "@hilla/react-components/IntegerField";
import {DatePicker} from "@hilla/react-components/DatePicker";
import {TextArea} from "@hilla/react-components/TextArea";
import {CheckboxGroup} from "@hilla/react-components/CheckboxGroup";
import {Checkbox} from "@hilla/react-components/Checkbox";
import {useForm} from "@hilla/react-form";
import { readAsDataURL } from "promise-file-reader";
import CreateListingRequestModel from "Frontend/generated/cz/asen/fridge/endpoint/request/CreateListingRequestModel";
import {FoodListingEndpoint} from "Frontend/generated/endpoints";
import Allergens from "Frontend/generated/cz/asen/fridge/domain/enums/Allergens";
import {useNavigate} from "react-router-dom";

const FoodListingCreateView: React.FC = () => {
    const navigate = useNavigate();
    const { model, submit, value,  field } = useForm(CreateListingRequestModel, {
        onSubmit: async (request) => {
            await FoodListingEndpoint.createListing(request)
            navigate("/food-listings")
        }
    })

    const [allergens, setAllergens] = useState<Allergens[]>([])

    useEffect(() => {
        FoodListingEndpoint.getAllergens().then(allergens => setAllergens(allergens));
    }, []);

    const responsiveSteps = [
        { minWidth: '0', columns: 1 },
        { minWidth: '500px', columns: 2 },
    ];

    return (
        <section className="flex justify-center items-start px-5 py-10 md:px-40 md:py-20 min-h-screen">
            <div className="max-w-7xl w-full">
                <div className="mb-4 md:mb-8 flex items-center justify-between">
                    <h1 className="text-lg md:text-2xl font-semibold">Create listing</h1>
                </div>
                <VerticalLayout theme="spacing">
                    <FormLayout responsiveSteps={responsiveSteps}>
                        <TextField label="Listing name" {...field(model.shortDescription)}/>
                        <TextField label="Pickup location" {...field(model.pickupLocation)}/>
                        <IntegerField label="Quantity" stepButtonsVisible min={0}  {...field(model.quantity)}/>
                        <DatePicker label="Expiration date" {...field(model.expiryDate)}/>
                        <CheckboxGroup label="Dietary info" {...field(model.allergens)}>
                            {allergens.map(option => (
                                <Checkbox key={option} value={option} label={option}/>
                            ))}
                        </CheckboxGroup>
                        <TextArea {...{colspan: 2}} label="Description" {...field(model.description)}/>
                        <p>Images: </p>
                        <Upload capture="camera" accept="image/*" onUploadBefore={
                            async (e) => {
                                const file = e.detail.file;
                                e.preventDefault();
                                if (value) {
                                    const base64Image = await readAsDataURL(file);
                                    if (value.base64Images) {
                                        value.base64Images.push(base64Image);
                                    } else {
                                        value.base64Images = [base64Image];
                                    }
                                }
                            }
                        }/>
                    </FormLayout>
                </VerticalLayout>
                <Button theme="primary" onClick={submit}>Create</Button>

            </div>
        </section>
);

};

export default FoodListingCreateView;