import {useEffect, useState} from 'react';
import axios from 'axios';

export interface LocationOption {
    place_id: string;
    licence: string;
    osm_type: string;
    osm_id: string;
    boundingbox: string[];
    lat: string;
    lon: string;
    display_name: string;
    class: string;
    type: string;
    importance: number;
}

export default function useLocationAutocomplete(query: string, debounceTime: number = 250): LocationOption[] {
    const [options, setOptions] = useState<LocationOption[]>([])
    const fetchOptions = () => {
        axios.get(`https://nominatim.openstreetmap.org/search?format=json&q=${query}`)
            .then(response => {
                setOptions(response.data);
            })
            .catch(error => {
                console.error(`Error fetching data from Nominatim API: ${error}`);
            });
    };

    useEffect(() => {
        const timerId = setTimeout(() => {
            if (query) {
                fetchOptions();
            } else {
                setOptions([]);
            }
        }, debounceTime)

        return () => {
            clearTimeout(timerId)
        }
    }, [query, debounceTime])

    return options
}