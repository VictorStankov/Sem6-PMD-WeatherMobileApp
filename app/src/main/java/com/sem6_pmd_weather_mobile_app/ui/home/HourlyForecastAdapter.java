package com.sem6_pmd_weather_mobile_app.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sem6_pmd_weather_mobile_app.R;
import com.sem6_pmd_weather_mobile_app.models.HourlyForecast;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder> {

    private List<HourlyForecast> hourlyForecasts = new ArrayList<>();

    public void setHourlyForecasts(List<HourlyForecast> hourlyForecasts) {
        this.hourlyForecasts = hourlyForecasts;
        this.notifyDataSetChanged();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView hour;
        private final TextView temp;
        private final ImageView weatherImage;
        private final ImageView rainImage;
        private final TextView rainChance;

        public TextView getHour() {
            return hour;
        }
        public TextView getTemp() {
            return temp;
        }

        public ImageView getWeatherImage() {
            return weatherImage;
        }

        public ImageView getRainImage() {
            return rainImage;
        }

        public TextView getRainChance() {
            return rainChance;
        }

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            hour = (TextView) view.findViewById(R.id.hour);
            temp = (TextView) view.findViewById(R.id.temperature_hour);
            weatherImage = (ImageView) view.findViewById(R.id.weatherImageHour);
            rainImage = (ImageView) view.findViewById(R.id.rain_image_hour);
            rainChance = (TextView) view.findViewById(R.id.rain_chance_hour);
        }
    }

    public HourlyForecastAdapter() {
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.forecast_hour_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getHour().setText(hourlyForecasts.get(position).hour);
        viewHolder.getTemp().setText(hourlyForecasts.get(position).temp);
        viewHolder.getWeatherImage().setImageDrawable(hourlyForecasts.get(position).weatherImage);
        viewHolder.getRainImage().setImageDrawable(hourlyForecasts.get(position).rainImage);
        viewHolder.getRainChance().setText(hourlyForecasts.get(position).rain_chance);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return hourlyForecasts.size();
    }
}